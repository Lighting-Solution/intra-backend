package com.ls.in.messenger.service;

import com.ls.in.messenger.dto.ChatRoomCreationRequest;
import com.ls.in.messenger.dto.ChatRoomDTO;

import java.util.List;

public interface RoomService {
	public List<ChatRoomDTO> getRooms(Integer empId);

	public void createChattingRoom(ChatRoomCreationRequest group);
	public void deleteChatRoom(ChatRoomDTO currentChat);
}
