package com.ls.in.messenger.controller;

import com.ls.in.messenger.dto.ChatMessageDTO;
import com.ls.in.messenger.dto.ChatRoomCreationRequest;
import com.ls.in.messenger.dto.ChatRoomDTO;
import com.ls.in.messenger.service.MessageService;
import com.ls.in.messenger.service.RoomService;
//import com.ls.in.messenger.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequiredArgsConstructor
//@Log4j2
//public class RoomController {
//
//	private final RoomService roomService;
//	private final MessageService messageService;
//
//	/**
//	 * 채팅방 목록 조회
//	 * Spring Security가 구축되지 않아서 넣은 임의의 PK값.
//	 * @return
//	 */
//	@GetMapping(value = "/api/rooms")
//	public List<ChatRoomDTO> rooms(){
//		log.info("# 채팅방 목록 가져오기");
//		return roomService.getRooms(1);
////		return roomService.getRooms(MessageUtil.getCurrentEmpId());
//	}
//	@GetMapping(value = "/api/rooms/{roomId}/messages")
//	public List<ChatMessageDTO> getMessages(@PathVariable Integer roomId) {
//		log.info("# 채팅방 메시지 가져오기");
//		return messageService.getMessagesByRoomId(roomId);
//	}
//}
@RestController
@RequiredArgsConstructor
@Log4j2
public class RoomController {

	private final RoomService roomService;
	private final MessageService messageService;

	/**
	 * description : 채팅방 목록 조회 기능
	 * @Param Integer EmpId
	 * Note: Spring Security가 구축되지 않아서 넣은 임의의 PK값.
	 * @return List<ChatRoomDTO>
	 */
	@GetMapping(value = "/api/rooms")
	public List<ChatRoomDTO> rooms(){
		log.info("# 채팅방 목록 가져오기");
		return roomService.getRooms(1);
	}



	/**
	 * description : 채팅방 생성하는 로직
	 * @param group : List<Integers> empIds, String name
	 * @return String
	 */
	@PostMapping("/api/room")
	public String createChatRoom(@RequestBody ChatRoomCreationRequest group) {
		log.info("Employee IDs: {}", group.getEmpIds());
		roomService.createChattingRoom(group);
		return "Chat room created successfully!";
	}

	/**
	 * description : 사용자의 채팅방을 삭제하는 로직.
	 * @param currentChat
	 * @return
	 */
	@PostMapping("/api/delRoom")
	public ResponseEntity<String> deleteChatRoom(@RequestBody ChatRoomDTO currentChat) {
		log.info("# 채팅방 삭제하기");
		roomService.deleteChatRoom(currentChat);
		return ResponseEntity.ok("상대방이 채팅방을 나갔습니다.");
	}
}
