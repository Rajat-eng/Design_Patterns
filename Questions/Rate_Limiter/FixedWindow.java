package Questions.Rate_Limiter;
import java.util.*;

public class FixedWindow implements RateLimiter {
    int maxRequest;
    int windowFrameSize;
    Map<String,Integer> count;
    Map<String,Long> timestamp;

    public FixedWindow(int maxRequest,int windowFrameSize){
        this.maxRequest=maxRequest;
        this.windowFrameSize=windowFrameSize;
    }
    public boolean isRequestAllowed(String clientId){
        long currentTime=System.currentTimeMillis();
        count.putIfAbsent(clientId,0);
        timestamp.putIfAbsent(clientId, currentTime);
        if(currentTime - timestamp.get(clientId)>windowFrameSize){
            count.put(clientId,0);
            timestamp.put(clientId,currentTime);
        }
        if(count.get(clientId) < maxRequest){
            count.put(clientId,count.get(clientId)+1);
            return true; 
        }
        return false;

    }
}
