package com.ls.in.document.service;

import com.ls.in.document.domain.model.DocumentBox;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

	public FileStorageService() {
	}

	/**
	 * 파일을 Stream 으로 저장하는 메서드
	 * @param file
	 * @param writerEmpId
	 * @return
	 */
	public String storeFile(MultipartFile file, Integer writerEmpId, String category) {
		if (file == null)
			return "";

		Path fileStorageLocation = Paths.get("src/main/resources/docs/" + category.toLowerCase() + "/" + writerEmpId).toAbsolutePath().normalize();
		try {
			Files.createDirectories(fileStorageLocation);
		} catch (Exception ex) {
			throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
		}

		String fileName = file.getOriginalFilename();

		try {
			// 파일명을 보안상의 이유로 깨끗하게 청소
//			fileName = fileName != null ? fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_") : "";
			Path targetLocation = fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
			throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	/**
	 * 파일을 다운로드 할 수 있게 하는 메서드
	 * @param storedPath
	 * @param fileName
	 * @return
	 */
	public ResponseEntity<Resource> getResourceResponse(String storedPath, String fileName) {
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

	public void deleteFile(DocumentBox documentBox) {
		String fileName = documentBox.getDocumentPath();
		String category = documentBox.getCategory().name();

		// 파일 경로를 생성합니다.
		Path fileStorageLocation = Paths.get("src/main/resources/docs/" + documentBox.getCategory().name().toLowerCase() + "/" + documentBox.getEmp().getEmpId()).toAbsolutePath().normalize();
		Path filePath = fileStorageLocation.resolve(fileName).normalize();

		try {
			// 파일이 존재하는지 확인하고 삭제합니다.
			if (Files.exists(filePath)) {
				Files.delete(filePath);
			}
		} catch (IOException ex) {
			throw new RuntimeException("Could not delete file " + fileName + ". Please try again!", ex);
		}
	}
}
