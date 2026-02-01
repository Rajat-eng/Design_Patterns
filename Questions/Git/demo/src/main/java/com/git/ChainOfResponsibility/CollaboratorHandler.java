package com.git.ChainOfResponsibility;

import com.git.Models.User.User;
import com.git.Enums.GitOperation;
import java.util.List;

/**
 * Concrete Handler:
 * Checks if user is in the collaborators list
 */
public class CollaboratorHandler extends AuthorizationHandler {
    
    private final List<User> collaborators;
    
    public CollaboratorHandler(List<User> collaborators) {
        this.collaborators = collaborators;
    }
    
    @Override
    public boolean authorize(User user, GitOperation operation, Object context) {
        if (!collaborators.contains(user)) {
            System.out.println("AUTHORIZATION DENIED: User '" + user.getName() + "' is not a collaborator");
            return false;
        }
        
        return passToNext(user, operation, context);
    }
}
