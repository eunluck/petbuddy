package com.petbuddy.api.controller.chatting;

import com.petbuddy.api.controller.ApiResult;
import com.petbuddy.api.service.chatting.ChattingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.petbuddy.api.configure.WebSocketConfigure.SUBSCRIBE;
import static com.petbuddy.api.controller.ApiResult.OK;
@Api(tags = "채팅방-WebSocket APIs")
@Controller
@RequiredArgsConstructor
public class WebSocketController {
    public static final String DESTINATION = SUBSCRIBE + "/chat/rooms/";

    private final SimpMessageSendingOperations messageSendingOperations;
    private final ChattingService chattingService;

    @ApiOperation(value = "채팅방 연결")
    @MessageMapping("/chat/messages")
    public void message(MessageRequest request) {
        MessageDto response = chattingService.save(request);// /sub/chat/rooms/1
        messageSendingOperations.convertAndSend(DESTINATION + request.getRoomId(), response);
    }


    @ApiOperation(value = "특정 채팅방 대화목록")
    @GetMapping("/chat/rooms/{roomId}/messages")
    public ApiResult<List<MessageDto>> showAll(@PathVariable Long roomId,
                                               @RequestParam int size, @RequestParam String lastMessageDate) {
        return OK(chattingService.showAllMessage(roomId, size, lastMessageDate));
    }

    @ApiOperation(value = "새로운 대화")
    @GetMapping("/chat/rooms/{roomId}/messages/new")
    public ApiResult<MessageDto> showLast(@PathVariable Long roomId) {
        MessageDto response = chattingService.showLastMessage(roomId);
        return OK(response);
    }



/*
    @PostMapping
    public ApiResult<?> create(@RequestParam("partnerId")Long PartnerId,
                                       @AuthenticationPrincipal UserInfo loginUser) {
        Long chatRoomId = chatRoomService.create(request, buyer);

        return ResponseEntity
                .created(URI.create(CHAT_ROOM_API_URI + "/" + chatRoomId))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<ChatRoomResponse>> showAll(@LoginMember Member member) {
        List<ChatRoomResponse> responses = ChatRoomResponse.listOf(chatRoomDao.showAll(member),
                member);

        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> delete(@PathVariable Long roomId) {
        chatRoomService.delete(roomId);
        return ResponseEntity.noContent().build();
    }*/


}
