package com.petbuddy.api.controller.chatting;

import com.petbuddy.api.model.chatting.Message;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class MessageDto {

    private static final long GAP_OF_KST_AND_UTC = 9L;

    private Long id;
    private Long senderId;
    private String senderNickname;
    private Long roomId;
    private String content;
    private LocalDateTime createdTime;

    public MessageDto() {
    }

    public MessageDto(Long id, Long senderId, String senderNickname, Long roomId,
                           String content, LocalDateTime createdTime) {
        this.id = id;
        this.senderId = senderId;
        this.senderNickname = senderNickname;
        this.roomId = roomId;
        this.content = content;
        this.createdTime = createdTime;
    }

    public static MessageDto of(Message message) {
        return new MessageDto(message.getId(), message.getSenderId(),
                message.getSenderNickname(), message.getRoomId(), message.getContent(),
                message.getCreatedAt());
    }

    public static List<MessageDto> listOf(List<Message> messages) {
        return messages.stream()
                .map(MessageDto::of)
                .collect(toList());
    }

    public MessageDto adjustTime() {
        return new MessageDto(id, senderId, senderNickname, roomId, content,
                createdTime);
    }

    public Long getId() {
        return id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public Long getRoomId() {
        return roomId;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
}
