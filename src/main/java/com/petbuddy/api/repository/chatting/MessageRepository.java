package com.petbuddy.api.repository.chatting;

import com.petbuddy.api.model.chatting.Message;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByRoomIdAndCreatedAtIsBeforeOrderByCreatedAtDesc(Long roomId,
                                                                              LocalDateTime createdAt, PageRequest pageRequest);

    Optional<Message> findTopByRoomIdOrderByCreatedAtDesc(Long roomId);
}
