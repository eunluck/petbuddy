package com.petbuddy.api.repository.chatting;

import com.petbuddy.api.model.chatting.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChattingRepository extends JpaRepository<ChattingRoom, Long> {

    List<ChattingRoom> findAllByChattersOrderByCreatedAtDesc(Long chatterId);
}
