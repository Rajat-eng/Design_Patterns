package com.git;

import com.git.Models.User.User;
import com.git.Models.Commit.Commit;
import com.git.Facade.GitFacade;
import com.git.Builder.CommitBuilder;
import com.git.ChainOfResponsibility.*;
import com.git.Enums.*;
import java.util.Arrays;

/**
 * Git Version Control System
 * Demonstrates complete Git functionality with proper design patterns
 */
public class Main {
    private static GitFacade git;
    private static User alice, bob, charlie;
    
    public static void main(String[] args) {
        System.out.println("=== Git Version Control System ===\n");
        
        // Initialize repository and team
        initializeRepository();
        
        // Core Git workflows
        basicCommitWorkflow();
        featureBranchWorkflow();
        collaborationWorkflow();
        stashWorkflow();
        mergeStrategies();
        branchManagement();
        repositoryMaintenance();
        
        // Display final state
        displayRepositoryState();
    }
    
    /**
     * Initialize repository with team members
     */
    private static void initializeRepository() {
        System.out.println("\n=== INITIALIZING REPOSITORY ===");
        
        alice = new User("Alice", "alice@dev.com");
        bob = new User("Bob", "bob@dev.com");
        charlie = new User("Charlie", "charlie@dev.com");
        
        git = new GitFacade("MyProject", alice);
        git.addTeamMember(bob);
        
        System.out.println("✓ Repository 'MyProject' created");
        System.out.println("✓ Team: Alice (owner), Bob (collaborator)");
    }
    
    /**
     * Basic commit and file management
     */
    private static void basicCommitWorkflow() {
        System.out.println("\n=== BASIC WORKFLOW: Commits & Files ===");
        
        // Initial commit
        git.editFile("README.md", "# MyProject\nA collaborative project");
        git.editFile("src/Main.java", "public class Main { }");
        git.saveChanges("Initial commit", alice);
        
        // Using builder for complex commits
        Commit multiFileCommit = CommitBuilder.create()
            .withMessage("Add authentication module")
            .by(alice)
            .addFile("src/Auth.java", "public class Auth { }")
            .addFile("src/User.java", "public class User { }")
            .addFile("config/auth.properties", "jwt.secret=...")
            .build();
        
        System.out.println("✓ Created multi-file commit: " + multiFileCommit.getCommitId());
        System.out.println("  Files: " + multiFileCommit.getFileSnapshots().keySet());
        
        git.showStatus();
    }
    
    /**
     * Feature branch development
     */
    private static void featureBranchWorkflow() {
        System.out.println("\n=== FEATURE DEVELOPMENT ===");
        
        // Quick feature creation
        git.quickFeature("login", "src/Login.java", 
                        "public class Login { }", 
                        "Implement login feature", alice);
        
        System.out.println("✓ Feature 'login' created and committed");
        
        // Manual feature workflow
        git.createFeatureBranch("payment");
        git.switchBranch("feature/payment");
        git.editFile("src/Payment.java", "public class Payment { }");
        git.saveChanges("Add payment processing", bob);
        
        // Complete feature (merge and cleanup)
        git.completeFeature("payment", bob);
        
        System.out.println("✓ Feature 'payment' completed and merged");
        git.showBranches();
    }
    
    /**
     * Team collaboration with authorization
     */
    private static void collaborationWorkflow() {
        System.out.println("\n=== COLLABORATION & AUTHORIZATION ===");
        
        // Setup authorization chain
        AuthorizationHandler collaboratorCheck = new CollaboratorHandler(Arrays.asList(alice, bob));
        AuthorizationHandler branchProtection = new ProtectedBranchHandler();
        AuthorizationHandler destructiveOps = new DestructiveOperationHandler(Arrays.asList(alice));
        
        collaboratorCheck.setNext(branchProtection);
        branchProtection.setNext(destructiveOps);
        
        // Test scenarios
        testAuthorization(collaboratorCheck, alice, GitOperation.COMMIT, "main", 
                         "Alice commits to main");
        testAuthorization(collaboratorCheck, bob, GitOperation.BRANCH_DELETE, "feature/temp", 
                         "Bob deletes feature branch");
        testAuthorization(collaboratorCheck, alice, GitOperation.BRANCH_DELETE, "main", 
                         "Alice tries to delete main");
        testAuthorization(collaboratorCheck, charlie, GitOperation.COMMIT, "main", 
                         "Non-collaborator attempts commit");
        testAuthorization(collaboratorCheck, bob, GitOperation.PRUNE, null, 
                         "Bob tries repository cleanup");
    }
    
    /**
     * Work-in-progress management with stash
     */
    private static void stashWorkflow() {
        System.out.println("\n=== WORK-IN-PROGRESS MANAGEMENT ===");
        
        // Start experimental work
        git.createFeatureBranch("experiment");
        git.switchBranch("feature/experiment");
        git.editFile("src/Experiment.java", "// Experimental code...");
        
        // Stash incomplete work
        git.saveWork("WIP: Experimenting with new algorithm", alice);
        System.out.println("✓ Work stashed");
        
        // Handle urgent task
        git.switchToMain();
        git.editFile("HOTFIX.txt", "Critical security patch");
        git.saveChanges("Security hotfix", alice);
        System.out.println("✓ Hotfix applied");
        
        // Resume experimental work
        git.switchBranch("feature/experiment");
        git.restoreWork();
        System.out.println("✓ Experimental work restored");
        
        git.showStashedWork();
    }
    
    /**
     * Different merge strategies
     */
    private static void mergeStrategies() {
        System.out.println("\n=== MERGE STRATEGIES ===");
        
        // Create divergent branches
        git.switchToMain();
        git.createFeatureBranch("feature-a");
        git.switchBranch("feature/feature-a");
        git.editFile("FeatureA.java", "class FeatureA { }");
        git.saveChanges("Feature A implementation", alice);
        
        git.switchToMain();
        git.createFeatureBranch("feature-b");
        git.switchBranch("feature/feature-b");
        git.editFile("FeatureB.java", "class FeatureB { }");
        git.saveChanges("Feature B implementation", bob);
        
        // Demonstrate simple merge
        git.switchToMain();
        System.out.println("\n✓ Using SIMPLE merge strategy");
        git.setMergeStrategy(MergeType.SIMPLE);
        git.completeFeature("feature-a", alice);
        
        // Demonstrate rebase strategy
        System.out.println("\n✓ Using REBASE strategy (linear history)");
        git.setMergeStrategy(MergeType.REBASE);
        git.switchBranch("feature/feature-b");
        git.switchToMain();
        git.completeFeature("feature-b", bob);
        
        System.out.println("\nMerge strategies demonstrated:");
        System.out.println("  • SIMPLE: Preserves complete history with merge commits");
        System.out.println("  • REBASE: Creates clean linear history");
        System.out.println("  • THREE_WAY: Intelligent conflict resolution");
    }
    
    /**
     * Branch management and cleanup
     */
    private static void branchManagement() {
        System.out.println("\n=== BRANCH MANAGEMENT ===");
        
        git.showBranches();
        git.showHistory();
        
        System.out.println("\nBranch operations:");
        System.out.println("  • Create: feature/, bugfix/, release/ branches");
        System.out.println("  • Switch: Checkout branches with state preservation");
        System.out.println("  • Merge: Multiple strategies available");
        System.out.println("  • Delete: Cleanup after feature completion");
    }
    
    /**
     * Repository maintenance and garbage collection
     */
    private static void repositoryMaintenance() {
        System.out.println("\n=== REPOSITORY MAINTENANCE ===");
        
        git.findOrphanedCommits();
        git.garbageCollect();
        
        System.out.println("\n✓ Maintenance complete");
        System.out.println("  • Orphaned commits identified");
        System.out.println("  • Garbage collection performed");
        System.out.println("  • Repository optimized");
    }
    
    /**
     * Display final repository state
     */
    private static void displayRepositoryState() {
        System.out.println("\n=== FINAL REPOSITORY STATE ===");
        
        git.showStatus();
        git.showBranches();
        git.showCommitGraph();
        git.showAuditLog();
        
        System.out.println("\n=== GIT VCS FEATURES ===");
        System.out.println("✓ Commit management with DAG structure");
        System.out.println("✓ Branch creation and merging");
        System.out.println("✓ Multiple merge strategies (Simple, Rebase, 3-Way)");
        System.out.println("✓ Stash for work-in-progress");
        System.out.println("✓ Team collaboration with authorization");
        System.out.println("✓ Repository maintenance and cleanup");
        System.out.println("✓ Complete audit trail");
        System.out.println("✓ Type-safe operations");
    }
    
    /**
     * Helper method to test authorization
     */
    private static void testAuthorization(AuthorizationHandler handler, User user, 
                                         GitOperation operation, String target, String description) {
        System.out.println("\n" + description + ":");
        boolean allowed = handler.authorize(user, operation, target);
        System.out.println("  " + (allowed ? "✓ ALLOWED" : "✗ DENIED"));
    }
}
