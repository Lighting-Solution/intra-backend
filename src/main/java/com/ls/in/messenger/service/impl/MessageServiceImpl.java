package com.ls.in.messenger.service.impl;

import com.ls.in.messenger.domain.model.Message;
import com.ls.in.messenger.domain.model.MessageFile;
import com.ls.in.messenger.domain.model.RoomMember;
import com.ls.in.messenger.dto.ChatMessageDTO;
import com.ls.in.messenger.repository.MessageFilesRepository;
import com.ls.in.messenger.repository.MessageRepository;
import com.ls.in.messenger.repository.RoomMemberRepository;
import com.ls.in.messenger.service.MessageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

	private final MessageRepository messageRepository;
	private final RoomMemberRepository roomMemberRepository;
	private final MessageFilesRepository messageFilesRepository;

	@Transactional
	public void saveMessage(ChatMessageDTO chatMessageDTO) {
		RoomMember roomMember = roomMemberRepository.findRoomMemberByRoomIdAndEmpId(chatMessageDTO.getRoomId(), chatMessageDTO.getEmpId());
		List<RoomMember> roomMembers = roomMemberRepository.findRoomMembersByRoomIdExceptionMe(chatMessageDTO.getRoomId(), chatMessageDTO.getEmpId());
		roomMembers.forEach(RoomMember::updateNotificationStatusTrue);
		Message message = Message.createMessage(chatMessageDTO, roomMember);
		if(chatMessageDTO.getFileUrl()!=null){
			MessageFile messageFile = new MessageFile();
			messageFile.setFilePath(chatMessageDTO.getFileUrl());
			messageFile.setMessage(message); // MessageFile 객체의 message필드에 생성된 Message 객체 설정하기
			messageFilesRepository.save(messageFile);
			message.setMessageFile(messageFile);
		}
		messageRepository.save(message);
	}

	@Transactional
	public List<ChatMessageDTO> getMessagesByRoomId(Integer roomId, Integer empId) {
		// notification -> false
		roomMemberRepository.findRoomMemberByRoomIdAndEmpId(roomId, empId).updateNotificationStatusFalse();

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
						message.getMessageFile() != null ? message.getMessageFile().getFilePath() : null,
						message.getMessageSendTime().format(formatter)

				))
				.collect(Collectors.toList());
	}
}
