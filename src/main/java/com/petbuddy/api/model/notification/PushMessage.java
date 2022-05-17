package com.petbuddy.api.model.notification;

public class PushMessage {

    private final String title;

    private final String clickTarget;

    private final String message;

    public PushMessage(String title, String clickTarget, String message) {
        this.title = title;
        this.clickTarget = clickTarget;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getClickTarget() {
        return clickTarget;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "PushMessage{" +
                "title='" + title + '\'' +
                ", clickTarget='" + clickTarget + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}