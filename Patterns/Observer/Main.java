package Patterns.Observer;

import Patterns.Observer.Class.EmailSubscriber;
import Patterns.Observer.Class.MobileSubscriber;
import Patterns.Observer.Class.NewsPublisher;
import Patterns.Observer.Interface.Observer;

public class Main {
    public static void main(String[] args) {
        // Create Publisher (Subject)
        NewsPublisher newsPublisher = new NewsPublisher();

        // Create Subscribers (Observers)
        Observer emailSubscriber = new EmailSubscriber("user@example.com");
        Observer mobileSubscriber = new MobileSubscriber("123-456-7890");

        // Subscribe to notifications
        newsPublisher.subscribe(emailSubscriber);
        newsPublisher.subscribe(mobileSubscriber);

        // Publish news (Observers will be notified)
        newsPublisher.publishNews("Breaking News: Observer Pattern Implemented!");

        // Unsubscribe an observer
        newsPublisher.unsubscribe(emailSubscriber);

        // Publish another news (Only remaining subscribers will be notified)
        newsPublisher.publishNews("Another News Update!");
    }
}
