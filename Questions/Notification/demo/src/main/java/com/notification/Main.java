package com.notification;
import java.util.*;

import com.notification.Enums.NotificationType;
import com.notification.Factory.NotificationStrategyFactory;
import com.notification.Observer.*;

public class Main {
    public static void main(String[] args) {

        NotificationService notificationService = new NotificationService();

        Map<String, Object> config = new HashMap<>();
        config.put("logging", true);
        config.put("attempts", 3);

        notificationService.addObserver(
                new EmailNotificationObserver(
                        NotificationStrategyFactory.create(NotificationType.EMAIL, config)
                )
        );

        notificationService.addObserver(
                new SMSNotificatonObserver(
                        NotificationStrategyFactory.create(NotificationType.SMS, config)
                )
        );

        OrderService orderService = new OrderService(notificationService);
        orderService.placeOrder("OID123");
    }
}
