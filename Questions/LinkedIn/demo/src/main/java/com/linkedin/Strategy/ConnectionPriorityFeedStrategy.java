package com.linkedin.Strategy;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.linkedin.Models.Post;

public class ConnectionPriorityFeedStrategy implements FeedRankingStrategy {
    @Override
    public List<Post> rank(String viewerId, List<Post> posts, List<String> firstDegree, List<String> secondDegree, int limit) {
        Set<String> first = Set.copyOf(firstDegree);
        Set<String> second = Set.copyOf(secondDegree);

        return posts.stream()
                .sorted(Comparator
                        .comparingInt((Post p) -> connectionWeight(p.getAuthorId(), first, second)).reversed()
                        .thenComparing(Post::getCreatedAt).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    private int connectionWeight(String authorId, Set<String> first, Set<String> second) {
        if (first.contains(authorId)) {
            return 3;
        }
        if (second.contains(authorId)) {
            return 2;
        }
        return 1;
    }
}
