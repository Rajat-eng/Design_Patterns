package com.linkedin.Services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.linkedin.Enums.ConnectionRequestStatus;
import com.linkedin.Models.ConnectionRequest;

public class ConnectionService {
    private final Map<String, Set<String>> adjacencyList = new ConcurrentHashMap<>();
    private final Map<String, ConnectionRequest> pendingRequestsByPair = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> pendingReceivedByMember = new ConcurrentHashMap<>();
    private final ReadWriteLock graphLock = new ReentrantReadWriteLock();

    public void connect(String memberA, String memberB) {
        graphLock.writeLock().lock();
        try {
            adjacencyList.computeIfAbsent(memberA, ignored -> ConcurrentHashMap.newKeySet()).add(memberB);
            adjacencyList.computeIfAbsent(memberB, ignored -> ConcurrentHashMap.newKeySet()).add(memberA);
        } finally {
            graphLock.writeLock().unlock();
        }
    }

    public boolean sendConnectionRequest(String fromMemberId, String toMemberId) {
        if (fromMemberId == null || toMemberId == null || fromMemberId.equals(toMemberId)) {
            return false;
        }

        graphLock.writeLock().lock();
        try {
            if (adjacencyList.getOrDefault(fromMemberId, Set.of()).contains(toMemberId)) {
                return false;
            }

            String key = requestKey(fromMemberId, toMemberId);
            if (pendingRequestsByPair.containsKey(key)) {
                return false;
            }

            ConnectionRequest request = new ConnectionRequest(fromMemberId, toMemberId);
            pendingRequestsByPair.put(key, request);
            pendingReceivedByMember
                    .computeIfAbsent(toMemberId, ignored -> ConcurrentHashMap.newKeySet())
                    .add(fromMemberId);
            return true;
        } finally {
            graphLock.writeLock().unlock();
        }
    }

    public boolean acceptConnectionRequest(String receiverId, String requesterId) {
        graphLock.writeLock().lock();
        try {
            String key = requestKey(requesterId, receiverId);
            ConnectionRequest request = pendingRequestsByPair.get(key);
            if (request == null || request.getStatus() != ConnectionRequestStatus.PENDING) {
                return false;
            }

            request.setStatus(ConnectionRequestStatus.ACCEPTED);
            connect(requesterId, receiverId);
            removePendingTracking(receiverId, requesterId, key);
            return true;
        } finally {
            graphLock.writeLock().unlock();
        }
    }

    public boolean rejectConnectionRequest(String receiverId, String requesterId) {
        graphLock.writeLock().lock();
        try {
            String key = requestKey(requesterId, receiverId);
            ConnectionRequest request = pendingRequestsByPair.get(key);
            if (request == null || request.getStatus() != ConnectionRequestStatus.PENDING) {
                return false;
            }

            request.setStatus(ConnectionRequestStatus.REJECTED);
            removePendingTracking(receiverId, requesterId, key);
            return true;
        } finally {
            graphLock.writeLock().unlock();
        }
    }

    public List<String> getPendingRequestSenders(String receiverId) {
        graphLock.readLock().lock();
        try {
            return new ArrayList<>(pendingReceivedByMember.getOrDefault(receiverId, Set.of()));
        } finally {
            graphLock.readLock().unlock();
        }
    }

    public List<String> getFirstDegreeConnections(String memberId) {
        graphLock.readLock().lock();
        try {
            return new ArrayList<>(adjacencyList.getOrDefault(memberId, Set.of()));
        } finally {
            graphLock.readLock().unlock();
        }
    }

    public List<String> getSecondDegreeConnections(String memberId) {
        graphLock.readLock().lock();
        try {
            Set<String> firstDegree = new HashSet<>(adjacencyList.getOrDefault(memberId, Set.of()));
            Set<String> secondDegree = new HashSet<>();

            for (String friend : firstDegree) {
                for (String candidate : adjacencyList.getOrDefault(friend, Set.of())) {
                    if (!candidate.equals(memberId) && !firstDegree.contains(candidate)) {
                        secondDegree.add(candidate);
                    }
                }
            }
            return new ArrayList<>(secondDegree);
        } finally {
            graphLock.readLock().unlock();
        }
    }

    private String requestKey(String fromMemberId, String toMemberId) {
        return fromMemberId + "->" + toMemberId;
    }

    private void removePendingTracking(String receiverId, String requesterId, String requestKey) {
        pendingRequestsByPair.remove(requestKey);
        Set<String> senders = pendingReceivedByMember.get(receiverId);
        if (senders != null) {
            senders.remove(requesterId);
            if (senders.isEmpty()) {
                pendingReceivedByMember.remove(receiverId);
            }
        }
    }
}
