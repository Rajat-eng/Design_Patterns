package Questions.Rate_Limiter;

interface RateLimiter {
    boolean isRequestAllowed(String clientId); 
}