package com.notification.Adapter;

import com.notification.Builder.NotificationContent;

public class TwilioSMSAdapter implements ProviderAdapter {


    @Override
    public void send(String message) {
        NotificationContent content = new NotificationContent.Builder().setTitle("Order Confirmed!")
                .setBody("Your order OID123 has been placed successfully!")
                .setFooter("Thank you for ordering with us")
                .build();
        System.out.println("[Twilio] SMS sent: " + message);
    }
}
