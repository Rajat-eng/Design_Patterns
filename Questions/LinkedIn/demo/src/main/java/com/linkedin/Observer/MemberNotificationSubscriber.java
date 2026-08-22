package com.linkedin.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.linkedin.Models.Post;

public class MemberNotificationSubscriber implements ChannelSubscriber {
    private final String memberId;
    private final List<String> notifications = new ArrayList<>();
    private final Lock lock = new ReentrantLock();

    public MemberNotificationSubscriber(String memberId) {
        this.memberId = memberId;
    }

    @Override
    public void onPostPublished(String channelName, Post post) {
        lock.lock();
        try {
            notifications.add("New post in #" + channelName + " by " + post.getAuthorId() + ": " + post.getContent());
        } finally {
            lock.unlock();
        }
    }

    public List<String> getNotifications() {
        lock.lock();
        try {
            return new ArrayList<>(notifications);
        } finally {
            lock.unlock();
        }
    }

    public String getMemberId() {
        return memberId;
    }
}
