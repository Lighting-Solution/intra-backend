package com.ls.in.document.controller;

import com.ls.in.document.dto.DocumentDTO;
import com.ls.in.document.dto.DocumentDetailDTO;
import com.ls.in.document.dto.DocumentList;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentController {
	/**
	 * description : 문서 목록을 가져오는 엔드포인트
	 * @param documentDTO
	 * @return
	 */
	public Page<DocumentList> getDocs(@RequestBody DocumentDTO documentDTO);
	/**
	 * description 게시글을 생성하기 위한 EndPoint
	 * @param title
	 * @param content
	 * @param file
	 * @param category
	 * @param writerEmpId
	 * @return
	 */
	public ResponseEntity<String> createDocument(
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam(value = "file", required = false) MultipartFile file, // 각 유저별로 폴더를 관리해야함
			@RequestParam("category") String category,
			@RequestParam("writerEmpId") Integer writerEmpId);

	/**
	 * description 게시글의 파일을 다운로드 하기 위한 엔드포인트
	 * @param id DocumentBox의 ID값
	 * @return 성공 시 다운로드, 실패 시 에러..
	 */
	public ResponseEntity<Resource> downloadFile(@PathVariable Integer id);

	/**
	 * 문서 내용 가져오기
	 * @param id
	 * @return
	 */
	public ResponseEntity<DocumentDetailDTO> getDocumentDetail(@PathVariable Integer id);

	/**
	 * 문서 수정하기
	 * @param documentId
	 * @param title
	 * @param content
	 * @param file
	 * @param writerEmpId
	 * @return
	 */
	public ResponseEntity<DocumentDetailDTO> updateDocument(
			@RequestParam("documentId") Integer documentId,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam("writerEmpId") Integer writerEmpId);

	/**
	 * 문서 삭제하기
	 * @param id
	 * @return
	 */
	public String deleteDocument(@PathVariable Integer id);

}
