package com.ls.in.document.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentInitDTO {
	private String title;
	private String content;
	private String fileName;
	private String category;
	private Integer writerEmpId;
}
