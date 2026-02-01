package com.git.Services.Interfaces;

import java.util.List;

import com.git.Models.User.User;

/**
 * Interface Segregation Principle (ISP):
 * Separate interface for collaboration and access control.
 */
public interface ICollaborationService {
    void addCollaborator(User user, List<User> collaborators);
    void removeCollaborator(User user, List<User> collaborators);
    boolean isAuthorized(User user, List<User> collaborators);
    void listCollaborators(List<User> collaborators);
}
