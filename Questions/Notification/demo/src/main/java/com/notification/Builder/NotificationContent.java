package com.notification.Builder;

public class NotificationContent {
    private final String title;
    private final String body;
    private final String footer;

    // Private constructor
    private NotificationContent(Builder builder) {
        this.title = builder.title;
        this.body = builder.body;
        this.footer = builder.footer;
    }

    public String getTitle() { return title; }
    public String getBody() { return body; }
    public String getFooter() { return footer; }

    // Builder Class
    public static class Builder {
        private String title;
        private String body;
        private String footer;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public Builder setFooter(String footer) {
            this.footer = footer;
            return this;
        }

        public NotificationContent build() {
            // Validation rules can be added here
            return new NotificationContent(this);
        }
    }
}

