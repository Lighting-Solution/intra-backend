package com.ls.in.messenger.controller.impl;

import com.ls.in.messenger.controller.RoomController;
import com.ls.in.messenger.dto.ChatRoomCreationRequest;
import com.ls.in.messenger.dto.ChatRoomDTO;
import com.ls.in.messenger.service.impl.MessageServiceImpl;
import com.ls.in.messenger.service.impl.RoomServiceImpl;
//import com.ls.in.messenger.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Log4j2
public class RoomControllerImpl implements RoomController {

	private final RoomServiceImpl roomService;
	private final MessageServiceImpl messageService;


	@GetMapping(value = "/api/rooms/{id}")
	public List<ChatRoomDTO> rooms(@PathVariable Integer id) {
		log.info("# 채팅방 목록 가져오기");
		return roomService.getRooms(id);
	}


	@PostMapping("/api/room")
	public String createChatRoom(@RequestBody ChatRoomCreationRequest group) {
		log.info("Employe	e IDs: {}", group.getEmpIds());
		roomService.createChattingRoom(group);
		return "Chat room created successfully!";
	}


	@PostMapping("/api/delRoom")
	public ResponseEntity<String> deleteChatRoom(@RequestBody ChatRoomDTO currentChat) {
		log.info("# 채팅방 삭제하기");
		roomService.deleteChatRoom(currentChat);
		return ResponseEntity.ok("상대방이 채팅방을 나갔습니다.");
	}
}
