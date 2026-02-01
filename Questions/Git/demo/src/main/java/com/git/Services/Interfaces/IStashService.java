package com.git.Services.Interfaces;

import com.git.Models.Stash.Stash;
import com.git.Models.User.User;
import java.util.List;
import java.util.Map;

/**
 * Interface Segregation Principle (ISP):
 * Dedicated interface for stash operations
 */
public interface IStashService {
    Stash createStash(String message, User author, String branchName, Map<String, String> files);
    Stash applyStash(Stash stash, Map<String, String> workingDirectory);
    Stash popStash(List<Stash> stashes, Map<String, String> workingDirectory);
    boolean dropStash(String stashId, List<Stash> stashes);
    void listStashes(List<Stash> stashes);
    Stash getStash(String stashId, List<Stash> stashes);
}
