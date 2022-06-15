package com.petbuddy.api.controller.chatting;

import com.petbuddy.api.controller.ApiResult;
import com.petbuddy.api.error.NotFoundException;
import com.petbuddy.api.model.chatting.ChattingRoom;
import com.petbuddy.api.model.user.UserInfo;
import com.petbuddy.api.service.chatting.ChattingService;
import com.petbuddy.api.service.pet.PetService;
import com.petbuddy.api.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static com.petbuddy.api.controller.ApiResult.OK;

@RestController
@RequestMapping("api/chat/room")
@Api(tags = "채팅방 APIs")
@RequiredArgsConstructor
public class ChattingController {
    private final ChattingService chattingService;
    private final UserService userService;
    private final PetService petService;

    @ApiOperation(value = "대상 펫과의 채팅방 생성")
    @PostMapping
    public ApiResult<URI> create(@RequestParam("partnerId")Long partnerId,
                                       @AuthenticationPrincipal UserInfo loginUser) {

        ChattingRoom chattingRoom = chattingService.createChatRoom(petService.findById(partnerId).orElseThrow(() -> new NotFoundException(Long.class,partnerId)),
                userService.findById(loginUser.getId()).orElseThrow(() -> new NotFoundException(Long.class, loginUser.getId())));
        return OK(URI.create("/api/chat/rooms/" + chattingRoom.getId()));
    }

    @ApiOperation(value = "대표펫 채팅방 목록")
    @GetMapping
    public ApiResult<List<ChatRoomDto>> showAll(@AuthenticationPrincipal UserInfo loginUser) {
        return OK(chattingService.findAllByPetId(
                loginUser.getRepresentativePetId())
                .stream()
                .map(ChatRoomDto::new)
                .collect(Collectors.toList()));
    }

    @ApiOperation(value = "채팅방 삭제")
    @DeleteMapping("/{roomId}")
    public ApiResult<Void> delete(@PathVariable Long roomId) {
        chattingService.deleteChattingRoom(roomId);
        return OK(null);
    }


}
