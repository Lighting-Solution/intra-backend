package com.ls.in.messenger.controller.impl;

import com.ls.in.messenger.controller.MessageController;
import com.ls.in.messenger.dto.ChatMessageDTO;
import com.ls.in.messenger.dto.ChatRoomDTO;
import com.ls.in.messenger.service.impl.MessageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class MessageControllerImpl implements MessageController {

    private final MessageServiceImpl messageService;
    @PostMapping(value = "/api/rooms/messages")
    public List<ChatMessageDTO> getMessagesByRoomId(@RequestBody ChatRoomDTO currentChat) {
        return messageService.getMessagesByRoomId(currentChat.getRoomId(), currentChat.getMyEmpId());
    }
}


