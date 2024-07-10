package com.ls.in.document.controller;


import com.ls.in.document.dto.DocumentDTO;
import com.ls.in.document.dto.DocumentList;
import com.ls.in.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/document")
public class DocumentController {
	private final DocumentService documentService;
	@PostMapping("/api/publicDocs")
	public List<DocumentList> getPublicDocs(@RequestBody DocumentDTO documentDTO) {
		return null;
	}


	/**
	 * description 게시글을 생성하기 위한 EndPoint
	 * @param title
	 * @param content
	 * @param file
	 * @param category
	 * @param writerEmpId
	 * @return
	 */
	@PostMapping("/api/creation")
	public ResponseEntity<String> createDocument(
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam("category") String category,
			@RequestParam("writerEmpId") Integer writerEmpId) {
		String fileName = fileStorageService.storeFile(file);
		DocumentInitDTO document = new DocumentInitDTO(title, content, fileName, category, writerEmpId);
		documentService.saveDocument(document);
		return new ResponseEntity<>("Document created successfully", HttpStatus.OK);
	}

	/**
	 * description 게시글의 파일을 다운로드 하기 위한 엔드포인트
	 * @param id
	 * @return 성공 시 다운로드, 실패 시 에러..
	 */
	@GetMapping("/{id}/download")
	public ResponseEntity<Resource> downloadFile(@PathVariable Integer id) {
		String storedPath = "src/main/resources/docs";
		DocumentBox document = documentService.getDocumentById(id);
		String fileName = document.getDocumentPath();
		if (document.getCategory().name().equalsIgnoreCase("approval"))
			storedPath = "src/main/resources/approvalComplete";
		try {
			Path filePath = Paths.get(storedPath).resolve(fileName).normalize();
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

	@PutMapping("/update")
	public ResponseEntity<DocumentDetailDTO> updateDocument(
			@RequestParam("documentId") Integer documentId,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		// File path를 적는 로직을 작성해야함.
		documentService.updateDocument(documentId, title, content);
		if (file != null) {
			System.out.println("File: " + file.getOriginalFilename());
		}
		return ResponseEntity.ok(null);
	}

}
