package com.ls.in.messenger.dto;

import com.ls.in.global.emp.domain.model.Emp;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ChatRoomCreationRequest {
	private List<Integer> empIds;
	private String roomName;
}
