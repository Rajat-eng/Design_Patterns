package com.git.Facade;

import com.git.Models.Repository.GitRepository;
import com.git.Models.User.User;
import com.git.Models.Commit.Commit;
import com.git.Services.*;
import com.git.Services.Interfaces.*;
import com.git.Observers.*;
import com.git.Enums.MergeType;
import java.util.Set;

/**
 * Facade Pattern:
 * Provides a simplified, unified interface to the complex Git VCS subsystem.
 * Hides the complexity of multiple services, observers, and internal operations.
 * 
 * Benefits:
 * - Simplifies client code
 * - Reduces dependencies on subsystems
 * - Provides a cleaner API
 * - Makes the system easier to use
 */
public class GitFacade {
    
    private final GitRepository repository;
    private final GitEventPublisher eventPublisher;
    private final LoggingObserver loggingObserver;
    private final AuditObserver auditObserver;
    
    /**
     * Constructor initializes all subsystems
     */
    public GitFacade(String repositoryName, User creator) {
        // Initialize all services
        ICommitService commitService = new CommitService();
        IBranchService branchService = new BranchService();
        ICollaborationService collaborationService = new CollaborationService();
        IDAGService dagService = new DAGService();
        IStashService stashService = new StashService();
        ICommitDeletionService deletionService = new CommitDeletionService();
        IMergeStrategy mergeStrategy = new SimpleMergeStrategy();
        
        // Initialize observers
        this.eventPublisher = new GitEventPublisher();
        this.loggingObserver = new LoggingObserver();
        this.auditObserver = new AuditObserver();
        
        // Subscribe observers
        eventPublisher.subscribe(loggingObserver);
        eventPublisher.subscribe(auditObserver);
        
        // Create repository with all dependencies
        this.repository = new GitRepository(
            repositoryName,
            creator,
            commitService,
            branchService,
            collaborationService,
            dagService,
            stashService,
            deletionService,
            mergeStrategy,
            eventPublisher
        );
        
        // Add creator as collaborator
        repository.addCollaborator(creator);
    }
    
    // === Simplified File Operations ===
    
    public void editFile(String filename, String content) {
        repository.modifyFile(filename, content);
    }
    
    public void saveChanges(String message, User author) {
        repository.commit(message, author);
    }
    
    // === Simplified Branch Operations ===
    
    public void createFeatureBranch(String featureName) {
        String branchName = "feature/" + featureName;
        repository.createBranch(branchName);
    }
    
    public void createBugfixBranch(String bugName) {
        String branchName = "bugfix/" + bugName;
        repository.createBranch(branchName);
    }
    
    public void switchBranch(String branchName) {
        repository.checkout(branchName);
    }
    
    public void switchToMain() {
        repository.checkout("main");
    }
    
    public void mergeFeature(String featureName, User author) {
        String branchName = "feature/" + featureName;
        repository.merge(branchName, author);
    }
    
    // === Simplified Stash Operations ===
    
    public void saveWork(String description, User author) {
        repository.stash("WIP: " + description, author);
    }
    
    public void restoreWork() {
        repository.popStash();
    }
    
    public void showStashedWork() {
        repository.listStashes();
    }
    
    // === Simplified Information Display ===
    
    public void showHistory() {
        repository.log();
    }
    
    public void showStatus() {
        repository.status();
    }
    
    public void showBranches() {
        repository.listBranches();
    }
    
    public void showCommitGraph() {
        repository.visualizeDAG();
    }
    
    // === Simplified Maintenance Operations ===
    
    public void cleanupBranch(String branchName, User author) {
        repository.deleteBranch(branchName, author);
    }
    
    public void garbageCollect() {
        repository.pruneUnreachableCommits();
    }
    
    public void findOrphanedCommits() {
        Set<Commit> orphans = repository.findUnreachableCommits();
        System.out.println("\nOrphaned commits found: " + orphans.size());
        for (Commit commit : orphans) {
            System.out.println("  - " + commit.getCommitId() + ": " + commit.getMessage());
        }
    }
    
    // === Team Collaboration ===
    
    public void addTeamMember(User user) {
        repository.addCollaborator(user);
    }
    
    public void showTeam() {
        repository.status(); // Shows collaborators
    }
    
    // === Audit and Logging ===
    
    public void showAuditLog() {
        auditObserver.printAuditLog();
    }
    
    public void enableLogging() {
        eventPublisher.subscribe(loggingObserver);
    }
    
    public void disableLogging() {
        eventPublisher.unsubscribe(loggingObserver);
    }
    
    // === Quick Workflows ===
    
    /**
     * Quick workflow: Create feature branch, edit files, and commit
     */
    public void quickFeature(String featureName, String filename, String content, String message, User author) {
        createFeatureBranch(featureName);
        switchBranch("feature/" + featureName);
        editFile(filename, content);
        saveChanges(message, author);
    }
    
    /**
     * Quick workflow: Save work, switch to main, do hotfix, return to feature
     */
    public void quickHotfix(String currentBranch, String hotfixName, 
                           String filename, String content, String message, User author) {
        // Save current work
        saveWork("Before hotfix", author);
        
        // Create and switch to hotfix branch
        switchToMain();
        createBugfixBranch(hotfixName);
        switchBranch("bugfix/" + hotfixName);
        
        // Apply hotfix
        editFile(filename, content);
        saveChanges(message, author);
        
        // Merge to main
        switchToMain();
        repository.merge("bugfix/" + hotfixName, author);
        
        // Return to feature branch
        switchBranch(currentBranch);
        restoreWork();
    }
    
    /**
     * Complete feature workflow
     */
    public void completeFeature(String featureName, User author) {
        switchToMain();
        mergeFeature(featureName, author);
        cleanupBranch("feature/" + featureName, author);
    }
    
    // === Strategy Management ===
    
    /**
     * Change merge strategy dynamically
     */
    public void setMergeStrategy(MergeType type) {
        IMergeStrategy newStrategy;
        
        switch (type) {
            case SIMPLE:
                newStrategy = new SimpleMergeStrategy();
                break;
            case REBASE:
                newStrategy = new RebaseStrategy();
                break;
            default:
                newStrategy = new SimpleMergeStrategy();
                break;
        }
        
        repository.setMergeStrategy(newStrategy);
        System.out.println("Merge strategy changed to: " + type.getDisplayName());
    }
    
    /**
     * Rebase current branch onto another branch
     */
    public void rebaseOnto(String targetBranch, User author) {
        // Temporarily switch to rebase strategy
        IMergeStrategy originalStrategy = repository.getMergeStrategy();
        repository.setMergeStrategy(new RebaseStrategy());
        
        System.out.println("Rebasing onto " + targetBranch + "...");
        repository.merge(targetBranch, author);
        
        // Restore original strategy
        repository.setMergeStrategy(originalStrategy);
    }
    
    // === Access to underlying repository (if needed) ===
    
    public GitRepository getRepository() {
        return repository;
    }
}
