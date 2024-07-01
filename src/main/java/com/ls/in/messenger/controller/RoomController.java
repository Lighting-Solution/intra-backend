package com.ls.in.messenger.controller;

import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.messenger.service.RoomService;
//import com.ls.in.messenger.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Log4j2
public class RoomController {

	private final RoomService roomService;

	/**
	 * 채팅방 목록 조회
	 * @return
	 */
//	@GetMapping(value = "/rooms")
//	public Map<Integer, List<Emp>> rooms(){
//		log.info("# 채팅방 목록 가져오기");
//		return roomService.getRooms(MessageUtil.getCurrentEmpId());
//	}


	/**
	 * Spring Security가 구축되지 않아서 넣은 임의의 PK값.
	 * @return
	 */
	@GetMapping(value = "/rooms")
	public Map<Integer, List<String>> rooms(){
		log.info("# 채팅방 목록 가져오기");
		return roomService.getRooms(1);
	}

}
