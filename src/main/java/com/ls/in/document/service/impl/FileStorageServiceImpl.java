package com.ls.in.document.service.impl;

import com.ls.in.document.domain.model.DocumentBox;
import com.ls.in.document.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.concurrent.CompletableFuture;

@Service
public class FileStorageServiceImpl implements FileService {

	public FileStorageServiceImpl() {
	}


	@Async("taskExecutor")
	public CompletableFuture<String> storeFile(MultipartFile file, DocumentBox documentBox) {
		if (file == null)
			return CompletableFuture.completedFuture("");

		Path fileStorageLocation = Paths.get("src/main/resources/docs/" + documentBox.getDocumentId()).toAbsolutePath().normalize();
		try {
			Files.createDirectories(fileStorageLocation);
		} catch (Exception ex) {
			throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
		}

		String fileName = file.getOriginalFilename();

		try {
			Path targetLocation = fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return CompletableFuture.completedFuture(fileName);
		} catch (IOException ex) {
			throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	@Async("taskExecutor")
	public CompletableFuture<String> storeFileVersion2(MultipartFile file) {
		if (file == null)
			return CompletableFuture.completedFuture("");

		Path fileStorageLocation = Paths.get("src/main/resources/docs/tmp").toAbsolutePath().normalize();
		try {
			Files.createDirectories(fileStorageLocation);
		} catch (Exception ex) {
			throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
		}

		String fileName = file.getOriginalFilename();

		try {
			Path targetLocation = fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return CompletableFuture.completedFuture(fileName);
		} catch (IOException ex) {
			throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public void deleteFile(DocumentBox documentBox) {
		String fileName = documentBox.getDocumentPath();
		if (fileName == null)
			return ;
		// 파일 경로를 생성합니다.
		Path fileStorageLocation = Paths.get("src/main/resources/docs/" + documentBox.getDocumentId()).toAbsolutePath().normalize();
		Path filePath = fileStorageLocation.resolve(fileName).normalize();

		try {
			// 파일이 존재하는지 확인하고 삭제합니다.
			if (Files.exists(filePath)) {
				Files.delete(filePath);
			}
			Path directoryPath = fileStorageLocation;
			if (Files.isDirectory(directoryPath) && isDirectoryEmpty(directoryPath)) {
				Files.delete(directoryPath);  // 비어있는 디렉토리 삭제
			}

		} catch (IOException ex) {
			throw new RuntimeException("Could not delete file " + fileName + ". Please try again!", ex);
		}
	}

	private boolean isDirectoryEmpty(Path directory) throws IOException {
		try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
			return !dirStream.iterator().hasNext();
		}
	}


	public ResponseEntity<Resource> getResourceResponse(String storedPath, String fileName) {
		try {
			Path filePath = Paths.get(storedPath).resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
			if (resource.exists()) {
				return ResponseEntity.ok()
						.contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM)
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
						.body(resource);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
}
