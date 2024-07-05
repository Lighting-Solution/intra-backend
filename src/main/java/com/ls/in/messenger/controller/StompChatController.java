package com.ls.in.messenger.controller;

import com.ls.in.messenger.dto.ChatMessageDTO;
import com.ls.in.messenger.service.MessageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StompChatController {
	private final MessageService messageService;
	private final SimpMessagingTemplate template;
	private final String uploadDir = "C:/intra-backend/message_file";

	@MessageMapping(value = "/chat/enter")
	public void enter(ChatMessageDTO message){
		message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
		template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
	}

	@MessageMapping(value = "/chat/message")
	public void message(ChatMessageDTO message){
		messageService.saveMessage(message);
		template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
	}

	@PostMapping("/file/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			throw new RuntimeException("Failed to store empty file.");
		}

		String originalFileName = file.getOriginalFilename();
		String storedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
		File destinationFile = new File(uploadDir + File.separator + storedFileName);

		try {
			file.transferTo(destinationFile);
			return originalFileName + "::" + storedFileName;
		} catch (IOException e) {
			throw new RuntimeException("Failed to store file.", e);
		}
	}

	@GetMapping("/file/download/{filePath}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String filePath) {
		File file = new File(uploadDir + File.separator + filePath);

		if (!file.exists()) {
			throw new RuntimeException("File not found.");
		}

		Resource resource = new FileSystemResource(file);
		String encodedFileName = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8);
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName  + "\"")
				.body(resource);
	}
}
