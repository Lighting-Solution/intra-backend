package com.ls.in.document.service;

import com.ls.in.document.domain.model.DocumentBox;
import com.ls.in.document.dto.DocumentDTO;
import com.ls.in.document.dto.DocumentDetailDTO;
import com.ls.in.document.dto.DocumentInitDTO;
import org.springframework.data.domain.Page;

public interface DocumentService {
	public Page<DocumentBox> getDocuments(DocumentDTO documentDTO);
	public DocumentBox saveDocument(DocumentInitDTO document);
	public DocumentBox getDocumentById(Integer id);
	public DocumentDetailDTO updateDocument(DocumentBox documentBox, String title, String content, String fileName);
	public void deleteDocument(DocumentBox documentBox);
}
