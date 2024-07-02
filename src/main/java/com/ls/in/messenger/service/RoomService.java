package com.ls.in.messenger.service;

import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.messenger.dto.ChatRoomDTO;
import com.ls.in.messenger.repository.RoomMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
	private final RoomMemberRepository roomMemberRepository;

	@Transactional
	public List<ChatRoomDTO> getRooms(Integer id) {
		List<Integer> rooms = roomMemberRepository.findRoomIdsByEmpId(id);
		Map<Integer, List<Emp>> roomAndEmp = rooms.stream().collect(Collectors.toMap(
				roomId -> roomId,
				roomMemberRepository::findEmpByRoomId
		));
		return ChatRoomDTO.create(roomAndEmp);
	}
}
