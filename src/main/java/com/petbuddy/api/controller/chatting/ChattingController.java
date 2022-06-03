package com.petbuddy.api.controller.chatting;

import com.petbuddy.api.controller.ApiResult;
import com.petbuddy.api.service.chatting.ChattingService;
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

@Controller
@RequiredArgsConstructor
public class ChattingController {
    public static final String DESTINATION = SUBSCRIBE + "/chat/rooms/";
    public static final String MESSAGE_URI = "/chat/messages";
    public static final String MESSAGE_REST_URI = "/chat/rooms/{roomId}/messages";
    public static final String NEW = "/new";



    private final SimpMessageSendingOperations messageSendingOperations;
    private final ChattingService chattingService;

    @MessageMapping(MESSAGE_URI)
    public void message(MessageRequest request) {
        System.out.println("호출완료");
        MessageDto response = chattingService.save(request);
        messageSendingOperations.convertAndSend(DESTINATION + request.getRoomId(), response);
    }

    @GetMapping(MESSAGE_REST_URI)
    public ApiResult<List<MessageDto>> showAll(@PathVariable Long roomId,
                                               @RequestParam int size, @RequestParam String lastMessageDate) {
        System.out.println("호출완료2");
        return OK(chattingService.showAll(roomId, size, lastMessageDate));
    }

    @GetMapping(MESSAGE_REST_URI + NEW)
    public ApiResult<MessageDto> showLast(@PathVariable Long roomId) {
        System.out.println("호출완료3");
        MessageDto response = chattingService.showLast(roomId);
        return OK(response);
    }

}
