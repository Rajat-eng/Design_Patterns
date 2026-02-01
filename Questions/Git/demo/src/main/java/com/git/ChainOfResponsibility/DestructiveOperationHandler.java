package com.git.ChainOfResponsibility;

import com.git.Models.User.User;
import com.git.Enums.GitOperation;
import java.util.Arrays;
import java.util.List;

/**
 * Concrete Handler:
 * Checks if user has permission for destructive operations
 */
public class DestructiveOperationHandler extends AuthorizationHandler {
    
    private static final List<GitOperation> DESTRUCTIVE_OPS = Arrays.asList(
        GitOperation.DELETE_COMMIT,
        GitOperation.PRUNE,
        GitOperation.BRANCH_DELETE
    );
    
    private final List<User> admins;
    
    public DestructiveOperationHandler(List<User> admins) {
        this.admins = admins;
    }
    
    @Override
    public boolean authorize(User user, GitOperation operation, Object context) {
        if (DESTRUCTIVE_OPS.contains(operation)) {
            if (!admins.contains(user)) {
                System.out.println("AUTHORIZATION DENIED: User '" + user.getName() + 
                                 "' lacks permission for " + operation.getDisplayName());
                return false;
            }
        }
        
        return passToNext(user, operation, context);
    }
}
