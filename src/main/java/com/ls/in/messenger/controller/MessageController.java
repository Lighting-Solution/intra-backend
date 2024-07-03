package com.ls.in.messenger.controller;

import com.ls.in.messenger.dto.ChatMessageDTO;
import com.ls.in.messenger.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;


    @GetMapping("/api/rooms/{roomId}/messages")
    public List<ChatMessageDTO> getMessagesByRoomId(@PathVariable Integer roomId) {
        log.info("# 메시지 목록 가져오기 for room: " + roomId);
        return messageService.getMessagesByRoomId(roomId);
    }


}


/*
    @GetMapping("/{roomId}")
    public List<ChatMessageDTO> getMessagesByRoomId(@PathVariable Integer roomId) {
        log.info("# 채팅방 메시지 목록 가져오기");
        return messageService.getMessagesByRoomId(roomId);
    }
    */
