package com.linkedin.Models;

import java.util.List;

public class FeedPage {
    private final List<Post> posts;
    private final String nextCursor;
    private final boolean hasMore;

    public FeedPage(List<Post> posts, String nextCursor, boolean hasMore) {
        this.posts = posts;
        this.nextCursor = nextCursor;
        this.hasMore = hasMore;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public String getNextCursor() {
        return nextCursor;
    }

    public boolean isHasMore() {
        return hasMore;
    }
}
