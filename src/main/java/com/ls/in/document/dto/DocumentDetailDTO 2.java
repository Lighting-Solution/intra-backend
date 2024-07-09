package com.ls.in.document.dto;

import lombok.Data;

@Data
public class DocumentDetailDTO {
	private Integer documentId; // DocumentBoxId
	private Integer writerEmpId;
	private String title;
	private String category;
	private String writerName;
	private String content;
	private String filePath;
	private String createdAt;
	private String updatedAt;
}
