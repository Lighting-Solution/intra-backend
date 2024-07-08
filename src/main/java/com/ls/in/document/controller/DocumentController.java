package com.ls.in.document.controller;


import com.ls.in.document.dto.DocumentDTO;
import com.ls.in.document.dto.DocumentInitDTO;
import com.ls.in.document.dto.DocumentList;
import com.ls.in.document.service.DocumentService;
import com.ls.in.document.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/document")
public class DocumentController {
	private final DocumentService documentService;
	private final FileStorageService fileStorageService;
	@PostMapping("/api/docsList")
	public List<DocumentList> getPublicDocs(@RequestBody DocumentDTO documentDTO) {
		documentService.getDocs(documentDTO);
		return null;
	}

	@PostMapping("/api/creation")
	public ResponseEntity<String> createDocument(
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("file") MultipartFile file,
			@RequestParam("category") String category,
			@RequestParam("writerEmpId") Integer writerEmpId) {

		String fileName = fileStorageService.storeFile(file);

		DocumentInitDTO document = new DocumentInitDTO(title, content, fileName, category, writerEmpId);
		documentService.saveDocument(document);

		return new ResponseEntity<>("Document created successfully", HttpStatus.OK);
	}
}
