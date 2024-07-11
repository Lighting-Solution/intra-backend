package com.ls.in.document.controller.impl;


import com.ls.in.document.controller.DocumentController;
import com.ls.in.document.domain.model.DocumentBox;
import com.ls.in.document.dto.DocumentDTO;
import com.ls.in.document.dto.DocumentDetailDTO;
import com.ls.in.document.dto.DocumentInitDTO;
import com.ls.in.document.dto.DocumentList;
import com.ls.in.document.service.impl.DocumentServiceImpl;
import com.ls.in.document.service.impl.FileStorageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/document")
public class DocumentControllerImpl implements DocumentController {
	private final DocumentServiceImpl documentService;
	private final FileStorageServiceImpl fileStorageService;


	@PostMapping("/api/docsList")
	public Page<DocumentList> getDocs(@RequestBody DocumentDTO documentDTO) {
		log.info("## documentDTO={}", documentDTO);
		Page<DocumentBox> docs = null;
//		if (documentDTO.getSearchTerm() == null)
		docs = documentService.getDocuments(documentDTO);
		log.info("docs:{}", docs.toString());
		log.info("docs.content:{}", docs.getContent());
		log.info("docs.page:{}", docs.getTotalPages());

		return docs.map(documentService::convertToDocumentList);
	}


	@PostMapping("/api/creation")
	public ResponseEntity<String> createDocument(
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam(value = "file", required = false) MultipartFile file, // 각 유저별로 폴더를 관리해야함
			@RequestParam("category") String category,
			@RequestParam("writerEmpId") Integer writerEmpId) {
		String fileName = null;
		if (file != null)
			fileName = file.getOriginalFilename();
		DocumentInitDTO document = new DocumentInitDTO(title, content, fileName, category, writerEmpId);
		DocumentBox documentBox = documentService.saveDocument(document);
		fileStorageService.storeFile(file, documentBox);
		return new ResponseEntity<>("Document created successfully", HttpStatus.OK);
	}


	@GetMapping("/{id}/download")
	public ResponseEntity<Resource> downloadFile(@PathVariable Integer id) {
		DocumentBox document = documentService.getDocumentById(id);
		String storedPath = "src/main/resources/docs/" + id;
		String fileName = document.getDocumentPath();
		if (document.getCategory().name().equalsIgnoreCase("approval"))
			storedPath = "src/main/resources/approvalComplete";
		return fileStorageService.getResourceResponse(storedPath, fileName);
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
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam("writerEmpId") Integer writerEmpId) {
		// File path를 적는 로직을 작성해야함.

		DocumentBox documentBox = documentService.getDocumentById(documentId);
		String fileName = null;
		log.info("Check Update :{}", documentBox);
		if (file != null)
			fileName = fileStorageService.storeFile(file, documentBox);
		if (documentBox.getDocumentPath() != null)
			fileStorageService.deleteFile(documentBox);
		log.info("Check Update :{}", documentBox);
		ResponseEntity<DocumentDetailDTO> ok = ResponseEntity.ok(documentService.updateDocument(documentBox, title, content, fileName));
		return ok;
	}

	@DeleteMapping("/delete/{id}")
	public String deleteDocument(@PathVariable Integer id) {
		log.info("# DELETE MAPPING:{}",id);
		DocumentBox documentBox = documentService.getDocumentById(id);
		fileStorageService.deleteFile(documentBox);
		documentService.deleteDocument(documentBox);
		return "delete is OK!";
	}
}
