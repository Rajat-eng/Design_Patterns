package Patterns.Observer.Class;

import java.util.ArrayList;
import java.util.List;


import Patterns.Observer.Interface.*;

public class NewsPublisher implements Publisher {
    List<Observer> observers;
    public NewsPublisher(){
        this.observers=new ArrayList<>();
    }
    @Override
    public void subscribe(Observer observer){
        observers.add(observer);
    }
    @Override
    public void unsubscribe(Observer observer){
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void publishNews(String news) {
        System.out.println("Publishing News: " + news);
        notifyObservers(news);
    }
}
