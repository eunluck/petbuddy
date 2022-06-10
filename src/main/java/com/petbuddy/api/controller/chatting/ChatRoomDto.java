package com.petbuddy.api.controller.chatting;

import com.petbuddy.api.model.chatting.ChattingRoom;
import com.petbuddy.api.model.pet.Pet;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class ChatRoomDto {
    @ApiModelProperty(value = "PK", required = true)
    private Long roomId;
    @ApiModelProperty(value = "채팅 참여 팻", required = true)
    private List<Pet> pets;
    @ApiModelProperty(value = "채팅방 생성일시", required = true)
    private LocalDateTime createdAt;
    @ApiModelProperty(value = "채팅방 수정일시", required = true)
    private LocalDateTime updatedAt;

    public ChatRoomDto(ChattingRoom source) {
        this.roomId = source.getId();
        this.pets = source.getChatters();
        this.createdAt = source.getCreatedAt();
        this.updatedAt = source.getUpdatedAt();
    }

}
