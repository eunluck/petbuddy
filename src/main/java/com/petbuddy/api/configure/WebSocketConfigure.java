package com.petbuddy.api.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfigure implements WebSocketMessageBrokerConfigurer {
    public static final String SUBSCRIBE = "/sub";
    public static final String PUBLISH = "/pub";
    public static final String ENDPOINT = "/chat";

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes(PUBLISH);
        //ApplicationDestinationPrefixes를 지정하면 대상 헤더가 시작되는 STOMP 메시지는 해당 클래스의 메서드로 라우팅된다.

        registry.enableSimpleBroker(SUBSCRIBE);

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(ENDPOINT).setAllowedOriginPatterns("*").withSockJS();

        /*
        *
        *  sockJs 클라이언트가 Websocket 핸드셰이크를 하기 위해 연결할 endpoint를 지정할 수 있다.
        * 클라이언트가 연결되고 http://localhost:8080/endpoint/info?t=12312312으로 웹소켓 통신이 가능한지 확인한 후,
        * 응답이 websocket:true 이면 웹소켓 연결된다.
        *
        * */
    }
}
