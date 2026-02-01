package com.git.Services;

import com.git.Models.User.User;
import com.git.Services.Interfaces.ICollaborationService;
import java.util.List;

/**
 * Single Responsibility Principle (SRP):
 * This class handles ONLY collaboration and access control.
 */
public class CollaborationService implements ICollaborationService {
    
    @Override
    public void addCollaborator(User user, List<User> collaborators) {
        if (!collaborators.contains(user)) {
            collaborators.add(user);
            System.out.println("Added collaborator: " + user);
        } else {
            System.out.println("User " + user + " is already a collaborator");
        }
    }
    
    @Override
    public void removeCollaborator(User user, List<User> collaborators) {
        if (collaborators.remove(user)) {
            System.out.println("Removed collaborator: " + user);
        } else {
            System.out.println("User " + user + " is not a collaborator");
        }
    }
    
    @Override
    public boolean isAuthorized(User user, List<User> collaborators) {
        return collaborators.contains(user);
    }
    
    @Override
    public void listCollaborators(List<User> collaborators) {
        System.out.println("\nCollaborators:");
        for (User user : collaborators) {
            System.out.println("  - " + user);
        }
    }
}
