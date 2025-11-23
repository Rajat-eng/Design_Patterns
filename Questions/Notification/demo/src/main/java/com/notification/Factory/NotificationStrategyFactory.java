package com.notification.Factory;
import java.util.Map;

import com.notification.Adapter.SendGridEmailAdapter;
import com.notification.Adapter.TwilioSMSAdapter;
import com.notification.Decorator.LoggingDecorator;
import com.notification.Decorator.RetryDecorator;
import com.notification.Enums.NotificationType;
import com.notification.Strategy.EmailNotificationStrategy;
import com.notification.Strategy.NotificationStrategy;
import com.notification.Strategy.SMSNotificationStrategy;

public class NotificationStrategyFactory {

    public static NotificationStrategy create(NotificationType type, Map<String, Object> config) {

        NotificationStrategy strategy;

        switch (type) {
            case EMAIL:
                strategy = new EmailNotificationStrategy(new SendGridEmailAdapter());
                break;
            case SMS:
                strategy = new SMSNotificationStrategy(new TwilioSMSAdapter());
                break;
            default:
                throw new IllegalArgumentException("Invalid type");
        }

        // Add logging decorator
        if (Boolean.TRUE.equals(config.get("logging"))) {
            strategy = new LoggingDecorator(strategy);
        }

        // Add retry decorator
        if (config.containsKey("attempts")) {
            strategy = new RetryDecorator(strategy, (Integer) config.get("attempts"));
        }

        return strategy;
    }
}
