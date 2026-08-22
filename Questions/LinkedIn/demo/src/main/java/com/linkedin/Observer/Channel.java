package com.linkedin.Observer;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.linkedin.Models.Post;

public class Channel {
    private final String name;
    private final Set<ChannelSubscriber> subscribers = ConcurrentHashMap.newKeySet();

    public Channel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void subscribe(ChannelSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(ChannelSubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void publish(Post post) {
        for (ChannelSubscriber subscriber : subscribers) {
            subscriber.onPostPublished(name, post);
        }
    }
}
