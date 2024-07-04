package com.ls.in.messenger.service;

import com.ls.in.messenger.domain.model.Message;
import com.ls.in.messenger.domain.model.RoomMember;
import com.ls.in.messenger.dto.ChatMessageDTO;
import com.ls.in.messenger.repository.MessageRepository;
import com.ls.in.messenger.repository.RoomMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor
public class MessageService {

	private final MessageRepository messageRepository;
	private final RoomMemberRepository roomMemberRepository;

	@Transactional
	public void saveMessage(ChatMessageDTO chatMessageDTO) {
		RoomMember roomMember = roomMemberRepository.findRoomMemberByRoomIdAndEmpId(chatMessageDTO.getRoomId(), chatMessageDTO.getEmpId());
		List<RoomMember> roomMembers = roomMemberRepository.findRoomMembersByRoomIdExceptionMe(chatMessageDTO.getRoomId(), chatMessageDTO.getEmpId());
		roomMembers.forEach(RoomMember::updateNotificationStatusTrue);
		Message message = Message.createMessage(chatMessageDTO, roomMember);
		messageRepository.save(message);
	}

	@Transactional
	public List<ChatMessageDTO> getMessagesByRoomId(Integer roomId) {
		// roomID를 통해 RoomMember 엔티티 리스트를 모두 가져옴
		List<RoomMember> roomMembers = roomMemberRepository.findByRoomRoomId(roomId);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		// RoomMember의 ID 리스트를 추출
		List<Integer> roomMemberIds = roomMembers.stream()
				.map(RoomMember::getRoomMemberId)
				.collect(Collectors.toList());

		// RoomMember ID 리스트를 사용해 메시지를 가져옴
		List<Message> messages = messageRepository.findByRoomMemberRoomMemberIdIn(roomMemberIds);

		// 메시지들을 DTO로 변환하여 반환
		return messages.stream()
				.map(message -> new ChatMessageDTO(
						message.getRoomMember().getRoom().getRoomId(),
						message.getRoomMember().getEmp().getEmpId(),
						message.getRoomMember().getEmp().getEmpName(),
						message.getMessageContent(),
						message.getMessageSendTime().format(formatter)

				))
				.collect(Collectors.toList());
	}
}
