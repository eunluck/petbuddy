/*
package com.petbuddy.api.configure.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSockChatHandler extends TextWebSocketHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);
        TextMessage textMessage = new TextMessage("Welcome");
        session.sendMessage(textMessage);
    }
}
*/
