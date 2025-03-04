package Questions.Rate_Limiter;

public class RateLimiterManager {
    public static RateLimiter createRateLimiter(String type, int maxRequest,int windowSize){
        // factory pattern
        switch (type.toLowerCase()) {
            case "fixed":
                return new FixedWindow(maxRequest, windowSize);
            case "window":
                return new SlidingWindow(maxRequest, windowSize);
            default:
                throw new IllegalArgumentException("not a valid rate limiter");
        }
    }
}
