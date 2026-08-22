package com.linkedin.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import com.linkedin.Enums.PostVisibility;
import com.linkedin.Models.FeedPage;
import com.linkedin.Models.Post;
import com.linkedin.Observer.Channel;
import com.linkedin.Observer.ChannelSubscriber;
import com.linkedin.Strategy.FeedRankingStrategy;

public class FeedService {
    private final ConnectionService connectionService;
    private final FeedRankingStrategy rankingStrategy;
    private final ExecutorService executor;

    private final AtomicInteger postIdGenerator = new AtomicInteger(1);
    private final List<Post> allPosts = new ArrayList<>();
    private final ReadWriteLock postLock = new ReentrantReadWriteLock();

    private final Map<String, Channel> channels = new ConcurrentHashMap<>();

    public FeedService(ConnectionService connectionService, FeedRankingStrategy rankingStrategy, ExecutorService executor) {
        this.connectionService = connectionService;
        this.rankingStrategy = rankingStrategy;
        this.executor = executor;
    }

    public CompletableFuture<Post> createPost(String authorId, String content, Optional<String> channelName) {
        return createPost(authorId, content, channelName, PostVisibility.PUBLIC);
    }

    public CompletableFuture<Post> createPost(String authorId, String content, Optional<String> channelName, PostVisibility visibility) {
        // supplyAsync is used to run the task asynchronously in a separate thread, and it returns a CompletableFuture that will be completed with the result of the task once it's done.
        return CompletableFuture.supplyAsync(() -> {
            String postId = "P-" + postIdGenerator.getAndIncrement();
            String channel = channelName.orElse(null);
            Post post = new Post(postId, authorId, content, channel, visibility);

            postLock.writeLock().lock();
            try {
                allPosts.add(post);
            } finally {
                postLock.writeLock().unlock();
            }

            if (channel != null) {
                channels.computeIfAbsent(channel, Channel::new).publish(post);
            }
            return post;
        }, executor);
    }

    public CompletableFuture<List<Post>> generateFeed(String viewerId, int limit) {
        return CompletableFuture.supplyAsync(() -> {
            return rankVisiblePosts(viewerId, limit);
        }, executor);
    }

    public CompletableFuture<FeedPage> generateFeedPage(String viewerId, int pageSize, Optional<String> cursorPostId) {
        return CompletableFuture.supplyAsync(() -> {
            List<Post> ranked = rankVisiblePosts(viewerId, Integer.MAX_VALUE);
            int start = 0;

            if (cursorPostId.isPresent()) {
                for (int i = 0; i < ranked.size(); i++) {
                    if (ranked.get(i).getId().equals(cursorPostId.get())) {
                        start = i + 1;
                        break;
                    }
                }
            }

            int safePageSize = Math.max(pageSize, 1);
            int end = Math.min(start + safePageSize, ranked.size());
            List<Post> pagePosts = new ArrayList<>(ranked.subList(start, end));
            boolean hasMore = end < ranked.size();
            String nextCursor = hasMore ? ranked.get(end - 1).getId() : null;
            return new FeedPage(pagePosts, nextCursor, hasMore);
        }, executor);
    }

    public void subscribe(String channelName, ChannelSubscriber subscriber) {
        channels.computeIfAbsent(channelName, Channel::new).subscribe(subscriber);
    }

    public void unsubscribe(String channelName, ChannelSubscriber subscriber) {
        Channel channel = channels.get(channelName);
        if (channel != null) {
            channel.unsubscribe(subscriber);
        }
    }

    private List<Post> rankVisiblePosts(String viewerId, int limit) {
        List<String> firstDegree = connectionService.getFirstDegreeConnections(viewerId);
        List<String> secondDegree = connectionService.getSecondDegreeConnections(viewerId);

        List<Post> snapshot;
        postLock.readLock().lock();
        try {
            snapshot = new ArrayList<>(allPosts);
        } finally {
            postLock.readLock().unlock();
        }

        List<Post> visiblePosts = snapshot.stream()
                .filter(post -> canViewPost(viewerId, post, firstDegree, secondDegree))
                .collect(Collectors.toList());

        return rankingStrategy.rank(viewerId, visiblePosts, firstDegree, secondDegree, limit);
    }

    private boolean canViewPost(String viewerId, Post post, List<String> firstDegree, List<String> secondDegree) {
        if (post.getAuthorId().equals(viewerId)) {
            return true;
        }

        return switch (post.getVisibility()) {
            case PUBLIC -> true;
            case FIRST_DEGREE -> firstDegree.contains(post.getAuthorId());
            case SECOND_DEGREE -> firstDegree.contains(post.getAuthorId()) || secondDegree.contains(post.getAuthorId());
            case PRIVATE -> false;
        };
    }
}
