package com.ls.in.document.controller;


import com.ls.in.document.domain.model.DocumentBox;
import com.ls.in.document.dto.DocumentDTO;
import com.ls.in.document.dto.DocumentDetailDTO;
import com.ls.in.document.dto.DocumentInitDTO;
import com.ls.in.document.dto.DocumentList;
import com.ls.in.document.service.DocumentService;
import com.ls.in.document.service.FileStorageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/document")
public class DocumentController {
	private final DocumentService documentService;
	private final FileStorageService fileStorageService;
	@PostMapping("/api/docsList")
	public Page<DocumentList> getPublicDocs(@RequestBody DocumentDTO documentDTO) {
//		List<DocumentBox> docs = documentService.getDocs(documentDTO);

		Pageable pageable = PageRequest.of(documentDTO.getPage(), documentDTO.getSize());
		Page<DocumentBox> docs = documentService.getDocs(documentDTO, pageable);

		log.info("documentDTO={}", documentDTO);
		log.info("docs:{}", docs.toString());
		log.info("docs.content:{}", docs.getContent());
		log.info("docs.page:{}", docs.getTotalPages());
		return docs.map(documentService::convertToDocumentList);
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

	@GetMapping("/{id}/download")
	public ResponseEntity<Resource> downloadFile(@PathVariable Integer id) {
		// Document 엔티티에서 파일 이름을 가져오는 로직을 추가해야 합니다.
		DocumentBox document = documentService.getDocumentById(id);
		String fileName = document.getDocumentPath();

		try {
			Path filePath = Paths.get("src/main/resources/docs").resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());

			if (resource.exists()) {
				return ResponseEntity.ok()
						.contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM)
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
						.body(resource);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/detail/{id}")
	public ResponseEntity<DocumentDetailDTO> getDocumentDetail(@PathVariable Integer id) {
		DocumentBox documentBox = documentService.getDocumentById(id);
		DocumentDetailDTO documentDetail = documentService.convertToDocumentDetail(documentBox);
		return ResponseEntity.ok(documentDetail);
	}
}
