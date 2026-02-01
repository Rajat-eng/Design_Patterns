package com.git.ChainOfResponsibility;

import com.git.Models.User.User;
import com.git.Enums.GitOperation;
import java.util.Arrays;
import java.util.List;

/**
 * Concrete Handler:
 * Checks if branch is protected (main, master, develop)
 */
public class ProtectedBranchHandler extends AuthorizationHandler {
    
    private static final List<String> PROTECTED_BRANCHES = Arrays.asList("main", "master", "develop");
    
    @Override
    public boolean authorize(User user, GitOperation operation, Object context) {
        // Only check for branch deletion operations
        if (operation == GitOperation.BRANCH_DELETE && context instanceof String) {
            String branchName = (String) context;
            
            if (PROTECTED_BRANCHES.contains(branchName)) {
                System.out.println("AUTHORIZATION DENIED: Cannot delete protected branch '" + branchName + "'");
                return false;
            }
        }
        
        return passToNext(user, operation, context);
    }
}
