package com.ls.in.messenger.dto;

import com.ls.in.global.emp.domain.model.Emp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class ChatRoomDTO {

	private Integer roomId;
	private Integer myEmpId;
	private String name;

	public static List<ChatRoomDTO> create(Map<Integer, List<Emp>> roomAndEmp, Integer myId){
		return roomAndEmp.entrySet().stream()
				.map(i -> new ChatRoomDTO(i.getKey(), myId,
						i.getValue().stream().map(Emp::getEmpName)
						.collect(Collectors.joining(","))))
				.collect(Collectors.toList());
	}
}
