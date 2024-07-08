package com.ls.in.document.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class DocumentDTO {
	private Integer empId;
	private String categoryName;
	private int page;
	private int size;
}
