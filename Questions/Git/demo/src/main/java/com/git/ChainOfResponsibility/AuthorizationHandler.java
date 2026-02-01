package com.git.ChainOfResponsibility;

import com.git.Models.User.User;
import com.git.Enums.GitOperation;

/**
 * Chain of Responsibility Pattern:
 * Abstract handler for authorization checks
 */
public abstract class AuthorizationHandler {
    
    protected AuthorizationHandler nextHandler;
    
    public void setNext(AuthorizationHandler handler) {
        this.nextHandler = handler;
    }
    
    public abstract boolean authorize(User user, GitOperation operation, Object context);
    
    protected boolean passToNext(User user, GitOperation operation, Object context) {
        if (nextHandler != null) {
            return nextHandler.authorize(user, operation, context);
        }
        return true; // Default: allow if no more handlers
    }
}
