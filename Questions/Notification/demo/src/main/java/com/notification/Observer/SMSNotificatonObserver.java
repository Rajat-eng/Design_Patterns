package com.notification.Observer;

import com.notification.Strategy.NotificationStrategy;

public class SMSNotificatonObserver extends NotificationObserver {
    public SMSNotificatonObserver(NotificationStrategy notificationStrategy){
        super(notificationStrategy);
    }
}
