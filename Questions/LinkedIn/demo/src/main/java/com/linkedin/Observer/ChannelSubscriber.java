package com.linkedin.Observer;

import com.linkedin.Models.Post;

public interface ChannelSubscriber {
    void onPostPublished(String channelName, Post post);
}
