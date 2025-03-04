package Questions.Rate_Limiter;

public class Main {
    public static void main(String[] Args){
        RateLimiter windowRateLimiter=RateLimiterManager.createRateLimiter("window", 5,60);
        for(int i=0;i<10;i++){
            boolean isAllowed=windowRateLimiter.isRequestAllowed(Integer.toString(1));
            System.out.println(isAllowed);
        }
    } 
}
