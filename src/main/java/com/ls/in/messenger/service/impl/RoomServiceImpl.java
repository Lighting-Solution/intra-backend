package com.ls.in.messenger.service.impl;

import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.repository.EmpRepository;
import com.ls.in.messenger.domain.model.Room;
import com.ls.in.messenger.domain.model.RoomMember;
import com.ls.in.messenger.dto.ChatRoomCreationRequest;
import com.ls.in.messenger.dto.ChatRoomDTO;
import com.ls.in.messenger.repository.MessageRepository;
import com.ls.in.messenger.repository.RoomMemberRepository;
import com.ls.in.messenger.repository.RoomRepository;
import com.ls.in.messenger.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
	private final RoomMemberRepository roomMemberRepository;
	private final RoomRepository roomRepository;
	private final EmpRepository empRepository;
	private final MessageRepository messageRepository;

	@Transactional
	public List<ChatRoomDTO> getRooms(Integer empId) {
		List<RoomMember> roomMembers = roomMemberRepository.findRoomIdsByEmpIdPresentStatusTrue(empId);
		Optional<Emp> myEmp = empRepository.findById(empId);
		Map<RoomMember, List<Emp>> roomAndEmp = roomMembers.stream()
				.collect(Collectors.toMap(
				roomMember -> roomMember,
				roomMember -> roomMemberRepository.findEmpByRoomIdExceptionMe(roomMember.getRoom().getRoomId(), empId)
		));
		return ChatRoomDTO.create(roomAndEmp, empId, myEmp.get().getEmpName());
	}

	@Transactional
	public void createChattingRoom(ChatRoomCreationRequest group) {
		Room room = Room.createRoom(group.getRoomName());
		roomRepository.save(room);
		group.getEmpIds().stream()
				.map(empRepository::findById)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(emp -> RoomMember.createRoomMember(emp, room))
				.forEach(roomMemberRepository::save);
	}

	@Transactional
	public void deleteChatRoom(ChatRoomDTO currentChat) {
		RoomMember roomMember = roomMemberRepository.findRoomMemberByRoomIdAndEmpId(currentChat.getRoomId(), currentChat.getMyEmpId());
		roomMember.updatePresentStatusFalse();
		List<RoomMember> roomMembers = roomMemberRepository.findByRoomRoomId(currentChat.getRoomId());
		Optional<Boolean> isUserOnRoom = roomMembers.stream().map(RoomMember::getPresentStatus)
				.filter(status -> status)
				.findAny();
		if (isUserOnRoom.isEmpty()) {
			roomMembers.stream().map(RoomMember::getRoomMemberId)
					.forEach(roomMemberId -> {
						messageRepository.deleteMessagesByRoomMemberId(roomMemberId);
						roomMemberRepository.deleteById(roomMemberId);
					});
			roomRepository.deleteById(currentChat.getRoomId());
		}
	}
}
