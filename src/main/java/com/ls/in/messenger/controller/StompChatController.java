package com.ls.in.messenger.controller;

import com.ls.in.messenger.dto.ChatMessageDTO;
import com.ls.in.messenger.service.MessageService;
import com.ls.in.messenger.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompChatController {
	private final MessageService messageService;
	private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
	private final RoomService roomService;
	//Client가 SEND할 수 있는 경로
	//stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
	//"/pub/chat/enter"

	/**
	 * description: enter메서드는 초대했을 때 동작하는 메서드다.
	 * 초대 버튼을 눌렀을 때 사원 아이디의 list가 전달된다
	 * @param message
	 */
	@MessageMapping(value = "/chat/enter")
	public void enter(ChatMessageDTO message){
		message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
		template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
	}

	@MessageMapping(value = "/chat/message")
	public void message(ChatMessageDTO message){
		messageService.saveMessage(message);
		template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
	}
}
