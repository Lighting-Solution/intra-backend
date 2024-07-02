package com.ls.in.messenger.service;

import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.repository.EmpRepository;
import com.ls.in.messenger.domain.model.Room;
import com.ls.in.messenger.domain.model.RoomMember;
import com.ls.in.messenger.dto.ChatRoomCreationRequest;
import com.ls.in.messenger.dto.ChatRoomDTO;
import com.ls.in.messenger.repository.RoomMemberRepository;
import com.ls.in.messenger.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
	private final RoomMemberRepository roomMemberRepository;
	private final RoomRepository roomRepository;
	private final EmpRepository empRepository;
	@Transactional
	public List<ChatRoomDTO> getRooms(Integer empId) {
		List<Integer> rooms = roomMemberRepository.findRoomIdsByEmpId(empId);
		Map<Integer, List<Emp>> roomAndEmp = rooms.stream().collect(Collectors.toMap(
				roomId -> roomId,
				roomId -> roomMemberRepository.findEmpByRoomIdExceptionMe(roomId, empId)
		));
		return ChatRoomDTO.create(roomAndEmp);
	}

	@Transactional
	public void createChattingRoom(ChatRoomCreationRequest group) {
		Room room = Room.createRoom(group.getName());
		roomRepository.save(room);
		group.getEmpIds().stream()
				.map(empRepository::findById)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(emp -> RoomMember.createRoomMember(emp, room))
				.map(roomMemberRepository::save);
	}
}
