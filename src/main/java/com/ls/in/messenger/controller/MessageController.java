package com.ls.in.messenger.controller;

import com.ls.in.messenger.dto.ChatMessageDTO;
import com.ls.in.messenger.dto.ChatRoomDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface MessageController {

	public List<ChatMessageDTO> getMessagesByRoomId(@RequestBody ChatRoomDTO currentChat);
}
