package com.ls.in.messenger.service;

import com.ls.in.messenger.dto.ChatMessageDTO;

import java.util.List;

public interface MessageService {
	public void saveMessage(ChatMessageDTO chatMessageDTO);
	public List<ChatMessageDTO> getMessagesByRoomId(Integer roomId, Integer empId);

}
