package com.git.Models.Repository;

import java.util.*;

import com.git.Models.Branch.Branch;
import com.git.Models.Commit.Commit;
import com.git.Models.User.User;
import com.git.Models.Stash.Stash;
import com.git.Services.CommitFactory;
import com.git.Services.Interfaces.*;
import com.git.Observers.GitEventPublisher;
import com.git.Observers.IGitObserver;
import com.git.Validators.*;

/**
 * Dependency Inversion Principle (DIP):
 * Repository depends on abstractions (interfaces) rather than concrete implementations.
 * This makes the code more flexible and testable.
 * 
 * Observer Pattern: Publishes events to observers
 * Command Pattern: Can be used with CommandInvoker
 */
public class GitRepository {
    private String name;
    private Map<String, Branch> branches;
    private Branch currentBranch;
    private Map<String, String> workingDirectory;
    private List<User> collaborators;
    private Set<Commit> allCommits;  // DAG: All nodes in the commit graph
    private List<Stash> stashes;  // Stash storage
    
    // Depend on abstractions (DIP)
    private final ICommitService commitService;
    private final IBranchService branchService;
    private final ICollaborationService collaborationService;
    private final IDAGService dagService;
    private final IStashService stashService;
    private final ICommitDeletionService deletionService;
    private IMergeStrategy mergeStrategy;
    
    // Observer Pattern
    private final GitEventPublisher eventPublisher;

    // Constructor injection for dependencies (DIP)
    public GitRepository(String name, User creator, 
                         ICommitService commitService,
                         IBranchService branchService,
                         ICollaborationService collaborationService,
                         IDAGService dagService,
                         IStashService stashService,
                         ICommitDeletionService deletionService,
                         IMergeStrategy mergeStrategy,
                         GitEventPublisher eventPublisher) {
        this.name = name;
        this.branches = new HashMap<>();
        this.workingDirectory = new HashMap<>();
        this.collaborators = new ArrayList<>();
        this.allCommits = new HashSet<>();
        this.stashes = new ArrayList<>();
        
        // Inject dependencies
        this.commitService = commitService;
        this.branchService = branchService;
        this.collaborationService = collaborationService;
        this.dagService = dagService;
        this.stashService = stashService;
        this.deletionService = deletionService;
        this.mergeStrategy = mergeStrategy;
        this.eventPublisher = eventPublisher;
        
        // Initialize repository
        collaborationService.addCollaborator(creator, collaborators);
        Commit initialCommit = CommitFactory.createInitialCommit(creator);
        allCommits.add(initialCommit);
        
        Branch master = branchService.createBranch("master", initialCommit);
        branches.put("master", master);
        this.currentBranch = master;
    }
    
    // Open/Closed Principle: Can change merge strategy without modifying code
    public void setMergeStrategy(IMergeStrategy strategy) {
        this.mergeStrategy = strategy;
        System.out.println("Merge strategy changed to: " + strategy.getStrategyName());
    }

    public void addCollaborator(User user) {
        collaborationService.addCollaborator(user, collaborators);
    }

    public void modifyFile(String filename, String content) {
        workingDirectory.put(filename, content);
        System.out.println("Modified: " + filename);
    }

    public Commit commit(String message, User author) {
        if (!collaborationService.isAuthorized(author, collaborators)) {
            System.out.println("ERROR: User " + author + " is not a collaborator!");
            return null;
        }

        if (workingDirectory.isEmpty()) {
            System.out.println("Nothing to commit, working directory clean");
            return null;
        }

        Commit newCommit = commitService.createCommit(message, author, currentBranch.getHead());
        
        // Add all files from working directory to commit
        for (Map.Entry<String, String> entry : workingDirectory.entrySet()) {
            newCommit.addFile(entry.getKey(), entry.getValue());
        }

        // Add to DAG
        allCommits.add(newCommit);
        
        // Update current branch head
        currentBranch.advanceHead(newCommit);
        workingDirectory.clear();
        
        System.out.println("Created commit: " + newCommit.getCommitId() + " on branch " + currentBranch.getName());
        
        // Notify observers
        eventPublisher.notifyCommitCreated(newCommit.getCommitId(), message, author.getName());
        
        return newCommit;
    }

    public Branch createBranch(String branchName) {
        if (branches.containsKey(branchName)) {
            System.out.println("Branch " + branchName + " already exists");
            return branches.get(branchName);
        }

        Branch newBranch = branchService.createBranch(branchName, currentBranch.getHead());
        branches.put(branchName, newBranch);
        System.out.println("Created branch: " + branchName);
        return newBranch;
    }

    public void checkout(String branchName) {
        Branch branch = branchService.getBranch(branchName, branches);
        if (branch == null) {
            System.out.println("Branch " + branchName + " does not exist");
            return;
        }

        if (!workingDirectory.isEmpty()) {
            System.out.println("ERROR: Uncommitted changes. Please commit or stash them first.");
            return;
        }

        currentBranch = branch;
        System.out.println("Switched to branch: " + branchName);
        
        // Restore files from latest commit
        restoreFromCommit(currentBranch.getHead());
    }

    public void merge(String branchName, User author) {
        Branch sourceBranch = branchService.getBranch(branchName, branches);
        if (sourceBranch == null) {
            System.out.println("Branch " + branchName + " does not exist");
            return;
        }

        if (sourceBranch == currentBranch) {
            System.out.println("Cannot merge a branch into itself");
            return;
        }

        // Use strategy pattern for merge
        Map<String, String> mergedFiles = mergeStrategy.merge(
            sourceBranch.getHead(), 
            currentBranch.getHead()
        );
        
        // Create merge commit with multiple parents (DAG)
        List<Commit> parents = Arrays.asList(currentBranch.getHead(), sourceBranch.getHead());
        Commit mergeCommit = commitService.createMergeCommit(
            "Merge branch '" + branchName + "' into " + currentBranch.getName(),
            author,
            parents
        );
        
        for (Map.Entry<String, String> entry : mergedFiles.entrySet()) {
            mergeCommit.addFile(entry.getKey(), entry.getValue());
        }

        // Add merge commit to DAG
        allCommits.add(mergeCommit);
        
        currentBranch.advanceHead(mergeCommit);
        System.out.println("Merged " + branchName + " into " + currentBranch.getName() + 
                         " using " + mergeStrategy.getStrategyName());
    }

    public void log() {
        System.out.println("\n=== Commit History for " + currentBranch.getName() + " ===");
        List<Commit> history = commitService.getCommitHistory(currentBranch.getHead());
        for (Commit commit : history) {
            System.out.println(commit);
        }
    }

    public void showCommit(String commitId) {
        Commit commit = commitService.findCommitById(commitId, currentBranch.getHead());
        if (commit != null) {
            commitService.displayCommitDetails(commit);
        } else {
            System.out.println("Commit " + commitId + " not found");
        }
    }

    public void restoreFromCommit(Commit commit) {
        if (commit == null) {
            System.out.println("No commit to restore from");
            return;
        }

        workingDirectory.clear();
        workingDirectory.putAll(commit.getFileSnapshots());
        System.out.println("Restored files from commit: " + commit.getCommitId());
    }

    public void checkoutCommit(String commitId) {
        Commit commit = commitService.findCommitById(commitId, currentBranch.getHead());
        if (commit != null) {
            restoreFromCommit(commit);
            System.out.println("Checked out commit: " + commitId + " (detached HEAD)");
        } else {
            System.out.println("Commit " + commitId + " not found");
        }
    }

    public void listBranches() {
        branchService.listBranches(branches, currentBranch);
    }

    public void status() {
        System.out.println("\n=== Repository Status ===");
        System.out.println("Repository: " + name);
        System.out.println("Current branch: " + currentBranch.getName());
        System.out.println("Latest commit: " + currentBranch.getHead().getCommitId());
        
        collaborationService.listCollaborators(collaborators);
        
        if (!workingDirectory.isEmpty()) {
            System.out.println("\nModified files:");
            for (String filename : workingDirectory.keySet()) {
                System.out.println("  - " + filename);
            }
        } else {
            System.out.println("\nWorking directory clean");
        }
    }
    
    public void visualizeDAG() {
        System.out.println("\n=== Git DAG Visualization ===");
        System.out.println("Total commits (nodes): " + allCommits.size());
        System.out.println("Total branches (pointers): " + branches.size());
        
        System.out.println("\nBranch pointers:");
        for (Map.Entry<String, Branch> entry : branches.entrySet()) {
            String indicator = (entry.getValue() == currentBranch) ? "* " : "  ";
            System.out.println(indicator + entry.getKey() + " -> " + entry.getValue().getHead().getCommitId());
        }
        
        dagService.visualizeDAG(currentBranch.getHead(), allCommits);
    }
    
    public Commit findCommonAncestor(String branchName) {
        Branch branch = branchService.getBranch(branchName, branches);
        if (branch == null) {
            System.out.println("Branch " + branchName + " does not exist");
            return null;
        }
        
        return dagService.findCommonAncestor(currentBranch.getHead(), branch.getHead());
    }
    
    // Stash operations
    public Stash stash(String message, User author) {
        if (workingDirectory.isEmpty()) {
            System.out.println("Nothing to stash, working directory clean");
            return null;
        }
        
        Stash newStash = stashService.createStash(message, author, currentBranch.getName(), workingDirectory);
        stashes.add(newStash);
        workingDirectory.clear();
        
        System.out.println("Created stash: " + newStash.getStashId());
        eventPublisher.notifyStashCreated(newStash.getStashId());
        
        return newStash;
    }
    
    public boolean applyStash(String stashId) {
        Stash stash = stashes.stream()
                .filter(s -> s.getStashId().equals(stashId))
                .findFirst()
                .orElse(null);
        
        if (stash == null) {
            System.out.println("Stash " + stashId + " not found");
            return false;
        }
        
        if (!workingDirectory.isEmpty()) {
            System.out.println("Working directory is not clean. Commit or stash changes first.");
            return false;
        }
        
        stashService.applyStash(stash, workingDirectory);
        System.out.println("Applied stash: " + stashId);
        return true;
    }
    
    public boolean popStash() {
        if (stashes.isEmpty()) {
            System.out.println("No stashes available");
            return false;
        }
        
        Stash latestStash = stashes.get(stashes.size() - 1);
        boolean applied = applyStash(latestStash.getStashId());
        
        if (applied) {
            stashService.dropStash(latestStash.getStashId(), stashes);
            System.out.println("Popped and dropped stash: " + latestStash.getStashId());
        }
        
        return applied;
    }
    
    public void dropStash(String stashId) {
        int sizeBefore = stashes.size();
        stashService.dropStash(stashId, stashes);
        if (stashes.size() < sizeBefore) {
            System.out.println("Dropped stash: " + stashId);
        } else {
            System.out.println("Stash " + stashId + " not found");
        }
    }
    
    public void listStashes() {
        stashService.listStashes(stashes);
    }
    
    // Deletion operations
    public boolean deleteBranch(String branchName, User deleter) {
        // Validate authorization
        if (!collaborationService.isAuthorized(deleter, collaborators)) {
            String error = "ERROR: User " + deleter + " is not a collaborator!";
            System.out.println(error);
            eventPublisher.notifyError(error);
            return false;
        }
        
        // Cannot delete current branch
        if (currentBranch.getName().equals(branchName)) {
            System.out.println("ERROR: Cannot delete the current branch");
            return false;
        }
        
        // Cannot delete main/master branch
        if (branchName.equals("main") || branchName.equals("master")) {
            System.out.println("ERROR: Cannot delete protected branch " + branchName);
            return false;
        }
        
        Branch removed = branches.remove(branchName);
        if (removed != null) {
            System.out.println("Deleted branch: " + branchName);
            return true;
        }
        
        System.out.println("Branch " + branchName + " not found");
        return false;
    }
    
    public boolean deleteCommit(String commitId, User deleter) {
        if (!collaborationService.isAuthorized(deleter, collaborators)) {
            String error = "ERROR: User " + deleter + " is not a collaborator!";
            System.out.println(error);
            eventPublisher.notifyError(error);
            return false;
        }
        
        boolean deleted = deletionService.deleteCommit(commitId, allCommits, branches);
        if (deleted) {
            System.out.println("Deleted commit: " + commitId);
        }
        return deleted;
    }
    
    public void pruneUnreachableCommits() {
        int sizeBefore = allCommits.size();
        deletionService.pruneUnreachableCommits(allCommits, branches);
        int pruned = sizeBefore - allCommits.size();
        System.out.println("Pruned " + pruned + " unreachable commits");
    }
    
    public Set<Commit> findUnreachableCommits() {
        return deletionService.findUnreachableCommits(allCommits, branches);
    }
    
    // Observer management
    public void subscribe(IGitObserver observer) {
        eventPublisher.subscribe(observer);
        System.out.println("Subscribed observer: " + observer.getClass().getSimpleName());
    }
    
    public void unsubscribe(IGitObserver observer) {
        eventPublisher.unsubscribe(observer);
        System.out.println("Unsubscribed observer: " + observer.getClass().getSimpleName());
    }
    
    // Strategy management - getMergeStrategy
    public IMergeStrategy getMergeStrategy() {
        return this.mergeStrategy;
    }

    public String getName() {
        return name;
    }

    public Branch getCurrentBranch() {
        return currentBranch;
    }
}
