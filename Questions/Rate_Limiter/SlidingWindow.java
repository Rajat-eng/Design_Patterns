package Questions.Rate_Limiter;
import java.util.*;


public class SlidingWindow implements RateLimiter{
    Map<String,Queue<Long>> map;
    int maxRequest;
    int windowFrameSize;

    SlidingWindow(int maxRequest,int windowFrameSize){
        this.maxRequest=maxRequest;
        this.windowFrameSize=windowFrameSize;
        this.map=new HashMap<>();
    }

    public boolean isRequestAllowed(String clientId){
        long currentTime=System.currentTimeMillis();
        map.putIfAbsent(clientId, new LinkedList<>());
        Queue<Long> timestamp=map.get(clientId);
        while(!timestamp.isEmpty() && currentTime-timestamp.peek() > windowFrameSize){
            timestamp.poll();
        }
        if(timestamp.size() < maxRequest){
            timestamp.add(currentTime);
            map.put(clientId,timestamp);
            return true;
        }
        return false;
    }
}
