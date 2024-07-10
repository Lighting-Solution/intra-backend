package com.ls.in.document.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DocumentDTO {
	private Integer empId;
	private String categoryName;
	private String searchTerm;
	private String searchType;
	private LocalDate startDate;
	private LocalDate endDate;
	private int page;
	private int size;
}
