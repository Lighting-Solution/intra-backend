package com.ls.in.messenger.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDTO {
	private Integer roomId;
	private Integer empId;
	private String writer;
	private String message;
}
