package com.petbuddy.api.service.chatting;

import com.petbuddy.api.controller.chatting.MessageDto;
import com.petbuddy.api.controller.chatting.MessageRequest;
import com.petbuddy.api.model.chatting.ChattingRoom;
import com.petbuddy.api.model.chatting.Message;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.user.UserInfo;
import com.petbuddy.api.repository.chatting.ChattingRepository;
import com.petbuddy.api.repository.chatting.MessageRepository;
import com.petbuddy.api.repository.pet.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChattingService {

    private final MessageRepository messageRepository;
    private final ChattingRepository chattingRepository;
    private final PetRepository petRepository;
    public MessageDto save(MessageRequest request) {
        Message message = request.toMessage();
        Message save = messageRepository.save(message.send(request.getPushToken()));

        return MessageDto.of(save);
    }

    public List<MessageDto> showAllMessage(Long roomId, int size, String lastMessageDate) {
        return MessageDto
                .listOf(messageRepository.findAllByRoomIdAndCreatedAtIsBeforeOrderByCreatedAtDesc(
                        roomId, LocalDateTime.parse(lastMessageDate), PageRequest.of(0, size)));
    }

    public MessageDto showLastMessage(Long roomId) {
        return MessageDto.of(messageRepository.findTopByRoomIdOrderByCreatedAtDesc(roomId)
                .orElse(new Message(null, 0L, "", 0L, "",
                        LocalDateTime.of(1, 1, 1, 1, 1, 1, 1))));
    }

    public ChattingRoom createChatRoom(Pet partnerPet, UserInfo loginUser){
        return chattingRepository.save(
                ChattingRoom.createChattingRoom(
                        loginUser.getRepresentativePetId(),
                        partnerPet.getId()));
    }



    public List<ChattingRoom> findAllByPetId(Long petId){
        return chattingRepository.findAllByChattersOrderByCreatedAtDesc(petId);
    }
    public void deleteChattingRoom(Long roomId){
        chattingRepository.deleteById(roomId);
    }

}
