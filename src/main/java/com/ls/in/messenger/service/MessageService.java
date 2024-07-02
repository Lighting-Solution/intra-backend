package com.ls.in.messenger.service;

import com.ls.in.messenger.domain.model.Message;
import com.ls.in.messenger.domain.model.RoomMember;
import com.ls.in.messenger.dto.ChatMessageDTO;
import com.ls.in.messenger.repository.MessageRepository;
import com.ls.in.messenger.repository.RoomMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

	private final MessageRepository messageRepository;
	private final RoomMemberRepository roomMemberRepository;

	public void saveMessage(ChatMessageDTO chatMessageDTO) {
		RoomMember roomMember = roomMemberRepository.findByRoomIdAndEmpId(chatMessageDTO.getRoomId(), chatMessageDTO.getEmpId());
		Message message = Message.createMessage(chatMessageDTO, roomMember);
		messageRepository.save(message);
	}
}
