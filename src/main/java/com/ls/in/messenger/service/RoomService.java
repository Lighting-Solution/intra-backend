package com.ls.in.messenger.service;

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
	public Map<Integer, List<String>> getRooms(Integer id) {
		List<Integer> rooms = roomMemberRepository.findRoomIdsByEmpId(id);
		return rooms.stream().collect(Collectors.toMap(
				roomId -> roomId,
				roomMemberRepository::findEmpNamesByRoomId
		));
	}
}
