package com.petbuddy.api.controller.chatting;

import com.petbuddy.api.model.chatting.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    private Long roomId;
    private Long senderId;
    private String senderNickname;
    private String message;
    private String pushToken;


    public Message toMessage() {
        return new Message(null,senderId, senderNickname, roomId, message, LocalDateTime.now());
    }

}
