package Patterns.Observer.Class;

import Patterns.Observer.Interface.Observer;

public class MobileSubscriber implements Observer{
    private String phoneNumber;

    public MobileSubscriber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void update(String message) {
        System.out.println("SMS sent to " + phoneNumber + ": " + message);
    }
}
