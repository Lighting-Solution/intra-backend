package com.ls.in.messenger.controller;

import com.ls.in.messenger.dto.ChatRoomCreationRequest;
import com.ls.in.messenger.dto.ChatRoomDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface RoomController {
	/**
	 * description : 채팅방 목록 조회 기능
	 *
	 * @return List<ChatRoomDTO>
	 * @Param Integer EmpId
	 * Note: Spring Security가 구축되지 않아서 넣은 임의의 PK값.
	 */
	public List<ChatRoomDTO> rooms(@PathVariable Integer id);

	/**
	 * description : 채팅방 생성하는 로직
	 *
	 * @param group : List<Integers> empIds, String name
	 * @return String
	 */
	public String createChatRoom(@RequestBody ChatRoomCreationRequest group);

	/**
	 * description : 사용자의 채팅방을 삭제하는 로직.
	 *
	 * @param currentChat
	 * @return
	 */
	public ResponseEntity<String> deleteChatRoom(@RequestBody ChatRoomDTO currentChat);
}
