package Patterns.Observer.Interface;

public interface Publisher {
    void subscribe(Observer observer);
    void unsubscribe(Observer observer);
    void notifyObservers(String message);
}
