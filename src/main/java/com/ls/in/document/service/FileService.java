package com.ls.in.document.service;

import com.ls.in.document.domain.model.DocumentBox;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public void deleteFile(DocumentBox documentBox);
	/**
	 * 파일을 Stream 으로 저장하는 메서드
	 * @param file
	 * @param documentBox
	 * @return
	 */
	public String storeFile(MultipartFile file, DocumentBox documentBox);

	/**
	 * 파일을 다운로드 할 수 있게 하는 메서드
	 * @param storedPath
	 * @param fileName
	 * @return ResponseEntity<Resource>
	 */
	public ResponseEntity<Resource> getResourceResponse(String storedPath, String fileName);
}
