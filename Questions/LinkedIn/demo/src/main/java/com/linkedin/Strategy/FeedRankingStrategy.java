package com.linkedin.Strategy;

import java.util.List;

import com.linkedin.Models.Post;

public interface FeedRankingStrategy {
    List<Post> rank(String viewerId, List<Post> posts, List<String> firstDegree, List<String> secondDegree, int limit);
}
