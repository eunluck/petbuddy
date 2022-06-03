package com.petbuddy.api.service.chatting;

import com.petbuddy.api.controller.chatting.MessageDto;
import com.petbuddy.api.controller.chatting.MessageRequest;
import com.petbuddy.api.model.chatting.Message;
import com.petbuddy.api.repository.chatting.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChattingService {

    private final MessageRepository messageRepository;


    public MessageDto save(MessageRequest request) {
        Message message = request.toMessage();
        // TODO: 2020/09/21 Member가 모듈로 분리 될때 토큰을 멤버 DB에서 조회해온다.
        Message save = messageRepository.save(message.send(request.getPushToken()));

        return MessageDto.of(save);
    }

    public List<MessageDto> showAll(Long roomId, int size, String lastMessageDate) {
        return MessageDto
                .listOf(messageRepository.findAllByRoomIdAndCreatedAtIsBeforeOrderByCreatedAtDesc(
                        roomId, LocalDateTime.parse(lastMessageDate), PageRequest.of(0, size)));
    }

    public MessageDto showLast(Long roomId) {
        return MessageDto.of(messageRepository.findTopByRoomIdOrderByCreatedAtDesc(roomId)
                .orElse(new Message(null, 0L, "", 0L, "",
                        LocalDateTime.of(1, 1, 1, 1, 1, 1, 1))));
    }
}
