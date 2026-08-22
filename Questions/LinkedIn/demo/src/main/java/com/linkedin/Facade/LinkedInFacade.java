package com.linkedin.Facade;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.linkedin.Enums.PostVisibility;
import com.linkedin.Models.Education;
import com.linkedin.Models.Experience;
import com.linkedin.Models.FeedPage;
import com.linkedin.Models.Member;
import com.linkedin.Models.Post;
import com.linkedin.Models.Profile;
import com.linkedin.Observer.MemberNotificationSubscriber;
import com.linkedin.Observer.ChannelSubscriber;
import com.linkedin.Services.ConnectionService;
import com.linkedin.Services.EngagementService;
import com.linkedin.Services.FeedService;
import com.linkedin.Strategy.ConnectionPriorityFeedStrategy;

public class LinkedInFacade {
    private final ExecutorService executor;
    private final ConnectionService connectionService;
    private final FeedService feedService;
    private final EngagementService engagementService;

    public LinkedInFacade() {
        this(Executors.newFixedThreadPool(8));
    }

    public LinkedInFacade(ExecutorService executor) {
        this.executor = executor;
        this.connectionService = new ConnectionService();
        this.feedService = new FeedService(connectionService, new ConnectionPriorityFeedStrategy(), executor);
        this.engagementService = new EngagementService(executor);
    }

    public void connectMembers(String memberAId, String memberBId) {
        connectionService.connect(memberAId, memberBId);
    }

    public boolean sendConnectionRequest(String fromMemberId, String toMemberId) {
        return connectionService.sendConnectionRequest(fromMemberId, toMemberId);
    }

    public boolean acceptConnectionRequest(String receiverId, String requesterId) {
        return connectionService.acceptConnectionRequest(receiverId, requesterId);
    }

    public boolean rejectConnectionRequest(String receiverId, String requesterId) {
        return connectionService.rejectConnectionRequest(receiverId, requesterId);
    }

    public List<String> getPendingConnectionRequests(String receiverId) {
        return connectionService.getPendingRequestSenders(receiverId);
    }

    public List<String> getFirstDegreeConnections(String memberId) {
        return connectionService.getFirstDegreeConnections(memberId);
    }

    public List<String> getSecondDegreeConnections(String memberId) {
        return connectionService.getSecondDegreeConnections(memberId);
    }

    public CompletableFuture<Post> createPost(String authorId, String content, Optional<String> channelName) {
        return feedService.createPost(authorId, content, channelName);
    }

    public CompletableFuture<Post> createPost(String authorId, String content, Optional<String> channelName, PostVisibility visibility) {
        return feedService.createPost(authorId, content, channelName, visibility);
    }

    public CompletableFuture<Void> likePost(Post post, String memberId) {
        return engagementService.likePost(post, memberId);
    }

    public CompletableFuture<Void> commentOnPost(Post post, String memberId, String comment) {
        return engagementService.commentOnPost(post, memberId, comment);
    }

    public CompletableFuture<List<Post>> generateFeed(String viewerId, int limit) {
        return feedService.generateFeed(viewerId, limit);
    }

    public CompletableFuture<FeedPage> generateFeedPage(String viewerId, int pageSize, Optional<String> cursorPostId) {
        return feedService.generateFeedPage(viewerId, pageSize, cursorPostId);
    }

    public void subscribeToChannel(String channelName, ChannelSubscriber subscriber) {
        feedService.subscribe(channelName, subscriber);
    }

    public void unsubscribeFromChannel(String channelName, ChannelSubscriber subscriber) {
        feedService.unsubscribe(channelName, subscriber);
    }

    public void runDemoScenario() {
        Member rajat = new Member("U1", "Rajat", buildProfile("Backend engineer", "Scaler", "SDE", "NSIT", "BTech"));
        Member neha = new Member("U2", "Neha", buildProfile("ML engineer", "Meta", "MLE", "IITD", "MTech"));
        Member arjun = new Member("U3", "Arjun", buildProfile("Product manager", "Amazon", "PM", "BITS", "MBA"));
        Member ananya = new Member("U4", "Ananya", buildProfile("Cloud architect", "Google", "Architect", "IIIT", "BTech"));
        Member kabir = new Member("U5", "Kabir", buildProfile("Security specialist", "Cisco", "Security Engineer", "DTU", "BTech"));

        sendConnectionRequest(rajat.getId(), neha.getId());
        sendConnectionRequest(neha.getId(), arjun.getId());
        sendConnectionRequest(arjun.getId(), ananya.getId());
        sendConnectionRequest(kabir.getId(), neha.getId());

        acceptConnectionRequest(neha.getId(), rajat.getId());
        acceptConnectionRequest(arjun.getId(), neha.getId());
        acceptConnectionRequest(ananya.getId(), arjun.getId());
        rejectConnectionRequest(neha.getId(), kabir.getId());

        MemberNotificationSubscriber rajatTechSub = new MemberNotificationSubscriber(rajat.getId());
        subscribeToChannel("java", rajatTechSub);

        CompletableFuture<Post> p1 = createPost(neha.getId(), "Built a new recommendation model.", Optional.of("ai"), PostVisibility.FIRST_DEGREE);
        CompletableFuture<Post> p2 = createPost(arjun.getId(), "Launching our product in APAC.", Optional.of("product"), PostVisibility.SECOND_DEGREE);
        CompletableFuture<Post> p3 = createPost(ananya.getId(), "Java 21 virtual threads are great.", Optional.of("java"), PostVisibility.PUBLIC);
        CompletableFuture<Post> p4 = createPost(kabir.getId(), "Private incident postmortem.", Optional.of("security"), PostVisibility.PRIVATE);

        CompletableFuture.allOf(p1, p2, p3, p4).join();

        Post post1 = p1.join();
        Post post2 = p2.join();

        CompletableFuture<Void> l1 = likePost(post1, rajat.getId());
        CompletableFuture<Void> c1 = commentOnPost(post1, rajat.getId(), "Great work, congrats!");
        CompletableFuture<Void> l2 = likePost(post2, neha.getId());
        CompletableFuture<Void> c2 = commentOnPost(post2, rajat.getId(), "Looking forward to it.");

        CompletableFuture.allOf(l1, c1, l2, c2).join();

        FeedPage page1 = generateFeedPage(rajat.getId(), 2, Optional.empty()).join();
        FeedPage page2 = generateFeedPage(rajat.getId(), 2, Optional.ofNullable(page1.getNextCursor())).join();

        System.out.println("First-degree connections of Rajat: " + getFirstDegreeConnections(rajat.getId()));
        System.out.println("Second-degree connections of Rajat: " + getSecondDegreeConnections(rajat.getId()));
        System.out.println("Pending requests for Neha: " + getPendingConnectionRequests(neha.getId()));

        System.out.println("\nGenerated feed page 1 for Rajat:");
        for (Post post : page1.getPosts()) {
            System.out.println("PostId=" + post.getId()
                    + ", author=" + post.getAuthorId()
                    + ", channel=" + post.getChannelName()
                    + ", visibility=" + post.getVisibility()
                    + ", likes=" + post.getLikeCount()
                    + ", comments=" + post.getComments().size()
                    + ", text='" + post.getContent() + "'");
        }
        System.out.println("Next Cursor=" + page1.getNextCursor() + ", Has More=" + page1.isHasMore());

        System.out.println("\nGenerated feed page 2 for Rajat:");
        for (Post post : page2.getPosts()) {
            System.out.println("PostId=" + post.getId()
                    + ", author=" + post.getAuthorId()
                    + ", channel=" + post.getChannelName()
                    + ", visibility=" + post.getVisibility()
                    + ", likes=" + post.getLikeCount()
                    + ", comments=" + post.getComments().size()
                    + ", text='" + post.getContent() + "'");
        }
        System.out.println("Next Cursor=" + page2.getNextCursor() + ", Has More=" + page2.isHasMore());

        System.out.println("\nObserver notifications for Rajat:");
        rajatTechSub.getNotifications().forEach(System.out::println);
    }

    public void shutdown() {
        executor.shutdown();
    }

    private static Profile buildProfile(String summary, String company, String role, String institute, String degree) {
        return new Profile.Builder()
                .setSummary(summary)
                .addExperience(new Experience(company, role, 3))
                .addEducation(new Education(institute, degree, 2020))
                .build();
    }
}
