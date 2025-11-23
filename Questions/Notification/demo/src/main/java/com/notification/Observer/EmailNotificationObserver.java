package com.notification.Observer;


import com.notification.Strategy.NotificationStrategy;

public class EmailNotificationObserver extends NotificationObserver {
    public EmailNotificationObserver(NotificationStrategy notificationStrategy){
        super(notificationStrategy);
    }
}
