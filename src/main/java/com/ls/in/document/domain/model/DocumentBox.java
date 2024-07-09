package com.ls.in.document.domain.model;

import com.ls.in.document.dto.DocumentInitDTO;
import com.ls.in.document.util.Category;
import com.ls.in.global.emp.domain.model.Emp;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "documentBox")
public class DocumentBox {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "document_id")
	private Integer documentId;

	@Column(name = "title")
	private String documentTitle;

	@Column(name = "path")
	private String documentPath;

	@Column(name = "content")
	private String documentContent;

	@Column(name = "createdAt")
	private LocalDateTime documentCreatedAt;

	@Column(name = "updatedAt")
	private LocalDateTime documentUpdatedAt;

	@Column(name = "category")
	@Enumerated(EnumType.STRING)
	private Category category;

	@ManyToOne
	@JoinColumn(name = "emp_id")
	private Emp emp;

	public static DocumentBox createDocs(DocumentInitDTO documentInitDTO, Emp emp) {
		DocumentBox documentBox = new DocumentBox();
		documentBox.documentTitle = documentInitDTO.getTitle();
		documentBox.documentContent = documentInitDTO.getContent();
		documentBox.category = Category.fromCategoryName(documentInitDTO.getCategory().trim());
		documentBox.emp = emp;
		documentBox.documentPath = documentInitDTO.getFileName();
		documentBox.documentCreatedAt = LocalDateTime.now();
		documentBox.documentUpdatedAt = LocalDateTime.now();
		return documentBox;
	}
}
