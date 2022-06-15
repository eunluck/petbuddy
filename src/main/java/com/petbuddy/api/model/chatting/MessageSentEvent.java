package com.petbuddy.api.model.chatting;

import org.springframework.context.ApplicationEvent;

public class MessageSentEvent extends ApplicationEvent {
    private final Message message;
    private final String pushToken;

    public MessageSentEvent(Object source, String pushToken) {
        super(source);
        this.message = (Message)source;
        this.pushToken = pushToken;
    }

    public Message getMessage() {
        return message;
    }

    public String getPushToken() {
        return pushToken;
    }
}
