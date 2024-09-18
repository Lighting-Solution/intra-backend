package com.ls.in.document.controller.impl;


import com.ls.in.document.controller.DocumentController;
import com.ls.in.document.domain.model.DocumentBox;
import com.ls.in.document.dto.DocumentDTO;
import com.ls.in.document.dto.DocumentDetailDTO;
import com.ls.in.document.dto.DocumentInitDTO;
import com.ls.in.document.dto.DocumentList;
import com.ls.in.document.service.impl.DocumentServiceImpl;
import com.ls.in.document.service.impl.FileStorageServiceImpl;
import com.ls.in.messenger.util.MeasureExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/document")
public class DocumentControllerImpl implements DocumentController {
	private final DocumentServiceImpl documentService;
	private final FileStorageServiceImpl fileStorageService;


	@PostMapping("/api/docsList")
	public Page<DocumentList> getDocs(@RequestBody DocumentDTO documentDTO) {
//		log.info("## documentDTO={}", documentDTO);
		Page<DocumentBox> docs = null;
		docs = documentService.getDocuments(documentDTO);
//		log.info("docs:{}", docs.toString());
//		log.info("docs.content:{}", docs.getContent());
//		log.info("docs.page:{}", docs.getTotalPages());

		return docs.map(documentService::convertToDocumentList);
	}

	@PostMapping("/api/creation")
	public ResponseEntity<String> createDocument(
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam("category") String category,
			@RequestParam("writerEmpId") Integer writerEmpId) {

		// 1. 파일을 임시 경로에 먼저 저장
		CompletableFuture<String> futureFile = fileStorageService.storeFileVersion2(file);

		// 2. DB에 document 저장 (documentBox 생성)
		String fileName = null;
		if (file != null)
			fileName = file.getOriginalFilename();
		DocumentInitDTO document = new DocumentInitDTO(title, content, fileName, category, writerEmpId);
		DocumentBox documentBox = documentService.saveDocument(document); // AutoIncrement된 PK 생성

		// 3. 임시 저장된 파일을 DocumentBox의 PK를 이용해 새로운 경로로 이동
		futureFile.thenAccept(filePath -> {
			try {
				// 생성된 DocumentBox의 ID로 새로운 폴더 생성
				Path newDirectory = Paths.get("src/main/resources/docs/" + documentBox.getDocumentId()).toAbsolutePath().normalize();
				Files.createDirectories(newDirectory); // 새로운 폴더 생성

				// 임시 폴더에 저장된 파일을 새로운 경로로 이동
				Path sourceFilePath = Paths.get("src/main/resources/docs/tmp").resolve(filePath);
				Path targetFilePath = newDirectory.resolve(filePath);
				Files.move(sourceFilePath, targetFilePath, StandardCopyOption.REPLACE_EXISTING);

			} catch (IOException e) {
				throw new RuntimeException("파일을 이동하는 동안 오류가 발생했습니다.", e);
			}
		}).exceptionally(ex -> {
			System.err.println("파일 처리 중 오류 발생: " + ex.getMessage());
			return null;
		});

		return new ResponseEntity<>("Document created successfully", HttpStatus.OK);
	}




	@GetMapping("/{id}/download")
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) {
		DocumentBox document = documentService.getDocumentById(id);
		String storedPath = "src/main/resources/docs/" + id;
		String fileName = document.getDocumentPath();
		Path path = Paths.get(storedPath).resolve(fileName);
		if (!Files.exists(path))
			return new ResponseEntity<>("파일이 아직 준비되지 않았습니다. 잠시 후 다시 시도해주세요.", HttpStatus.BAD_REQUEST);
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
			@RequestParam("writerEmpId") Integer writerEmpId) throws ExecutionException, InterruptedException {
		// File path를 적는 로직을 작성해야함.

		DocumentBox documentBox = documentService.getDocumentById(documentId);
		String fileName = null;
		CompletableFuture<String> future;
		log.info("Check Update :{}", documentBox);
		if (file != null) {
			future = fileStorageService.storeFile(file, documentBox);
		}
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
