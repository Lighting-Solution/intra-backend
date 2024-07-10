package com.ls.in.document.controller;


import com.ls.in.document.domain.model.DocumentBox;
import com.ls.in.document.dto.DocumentDTO;
import com.ls.in.document.dto.DocumentDetailDTO;
import com.ls.in.document.dto.DocumentInitDTO;
import com.ls.in.document.dto.DocumentList;
import com.ls.in.document.service.DocumentService;
import com.ls.in.document.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/document")
public class DocumentController {
	private final DocumentService documentService;
	private final FileStorageService fileStorageService;

	/**
	 * description : 문서 목록을 가져오는 엔드포인트
	 *
	 * @param documentDTO
	 * @return
	 */
	@PostMapping("/api/docsList")
	public Page<DocumentList> getDocs(@RequestBody DocumentDTO documentDTO) {
		Pageable pageable = PageRequest.of(documentDTO.getPage(), documentDTO.getSize());
		List<DocumentBox> docTest = new ArrayList<>();
		Page<DocumentBox> docs = new PageImpl<>(docTest, pageable, 0);
		log.info("documentDTO={}", documentDTO);

		if (documentDTO.getCategoryName() != null)
			docs = documentService.getDocs(documentDTO, pageable);
		log.info("docs:{}", docs.toString());
		log.info("docs.content:{}", docs.getContent());
		log.info("docs.page:{}", docs.getTotalPages());


		return docs.map(documentService::convertToDocumentList);
	}

	/**
	 * description 게시글을 생성하기 위한 EndPoint
	 *
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
			@RequestParam(value = "file", required = false) MultipartFile file, // 각 유저별로 폴더를 관리해야함
			@RequestParam("category") String category,
			@RequestParam("writerEmpId") Integer writerEmpId) {
		String fileName = fileStorageService.storeFile(file, writerEmpId, category);
		DocumentInitDTO document = new DocumentInitDTO(title, content, fileName, category, writerEmpId);
		documentService.saveDocument(document);
		return new ResponseEntity<>("Document created successfully", HttpStatus.OK);
	}

	/**
	 * description 게시글의 파일을 다운로드 하기 위한 엔드포인트
	 *
	 * @param id DocumentBox의 ID값
	 * @return 성공 시 다운로드, 실패 시 에러..
	 */
	@GetMapping("/{id}/download")
	public ResponseEntity<Resource> downloadFile(@PathVariable Integer id) {
		log.info("#################### 아오 다운로드 들어와?");
		DocumentBox document = documentService.getDocumentById(id);
		String storedPath = "src/main/resources/docs/" + document.getCategory().name().toLowerCase() + "/" + document.getEmp().getEmpId();
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
		String fileName = fileStorageService.storeFile(file, writerEmpId, documentBox.getCategory().name());
		fileStorageService.deleteFile(documentBox);
		log.info("Check Update :{}", fileName);
		return ResponseEntity.ok(documentService.updateDocument(documentBox, title, content, fileName));
	}

	@DeleteMapping("/delete/{id}")
	public String deleteDocument(@PathVariable Integer id) {
		DocumentBox documentBox = documentService.getDocumentById(id);
		fileStorageService.deleteFile(documentBox);
		documentService.deleteDocument(documentBox);
		return "delete is OK!";
	}
}
