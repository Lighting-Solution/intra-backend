package com.ls.in.messenger.dto;

import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.messenger.domain.model.RoomMember;
import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDTO {

	private Integer roomId;
	private Integer myEmpId;
	private String name;
	private String myName;
	private Boolean notificationStatus;

	public static List<ChatRoomDTO> create(Map<RoomMember, List<Emp>> roomAndEmp, Integer myId, String loginName){
		return roomAndEmp.entrySet().stream()
				.map(i -> new ChatRoomDTO(i.getKey().getRoom().getRoomId(),
						myId,
						i.getValue().stream().map(Emp::getEmpName).collect(Collectors.joining(",")),
						loginName,
						i.getKey().getNotificationStatus()
						))
				.collect(Collectors.toList());
	}
}
