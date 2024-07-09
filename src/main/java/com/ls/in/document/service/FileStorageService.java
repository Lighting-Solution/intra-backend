package com.ls.in.document.service;

import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

	public FileStorageService() {
		this.fileStorageLocation = Paths.get("src/main/resources/docs").toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
		}
	}

	public String storeFile(MultipartFile file) {
		if (file == null)
			return "";
		String fileName = file.getOriginalFilename();

		try {
			// 파일명을 보안상의 이유로 깨끗하게 청소
			fileName = fileName != null ? fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_") : "";
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
			throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}
}
