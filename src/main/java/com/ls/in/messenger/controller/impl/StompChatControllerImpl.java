package com.ls.in.messenger.controller.impl;

import com.ls.in.messenger.controller.StompChatController;
import com.ls.in.messenger.dto.ChatMessageDTO;
import com.ls.in.messenger.service.impl.MessageServiceImpl;

import com.ls.in.messenger.util.MeasureExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.Thread.sleep;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StompChatControllerImpl implements StompChatController {
	private final MessageServiceImpl messageService;
	private final SimpMessagingTemplate template;//특정 Broker로 메세지를 전달
	private final String uploadDir = "src/main/resources/message/";
	//Client가 SEND할 수 있는 경로
	//stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
	//"/pub/chat/enter"


	@MessageMapping(value = "/chat/enter")
	public void enter(ChatMessageDTO message) {
		message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
		template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
	}

	@MessageMapping(value = "/chat/message")
	public void message(ChatMessageDTO message) {
		log.info("# 채팅방 메시지 저장하기");
		messageService.saveMessage(message);
		template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
	}

//	@PostMapping("/file/upload")
//	public List<String> handleFileUpload(@RequestParam("files") MultipartFile[] files) {
//		if (files.length == 0) {
//			throw new RuntimeException("Failed to store empty file.");
//		}
//
//		log.info("# 채팅방 업로드 POST");
//
//		List<String> uploadedFilesInfo = new ArrayList<>();
//		Path fileStorageLocation = Paths.get("src/main/resources/message/").toAbsolutePath();
//
//		for (MultipartFile file : files) {
//			String originalFileName = file.getOriginalFilename();
//			String storedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
//
//			try {
//				Path targetName = fileStorageLocation.resolve(storedFileName);
//				Files.copy(file.getInputStream(), targetName, StandardCopyOption.REPLACE_EXISTING);
//
//				uploadedFilesInfo.add(originalFileName + "::" + storedFileName);
//
//			} catch (IOException e) {
//				throw new RuntimeException("Failed to store file: " + originalFileName, e);
//			}
//
//			try{
//				Thread.sleep(5000);
//			} catch (Exception e){
//				e.printStackTrace();
//			}
//		}
//
//		return uploadedFilesInfo; // 업로드된 파일 정보 리스트 반환
//	}

	@PostMapping("/file/upload")
	public List<String> handleFileUpload(@RequestParam("files") MultipartFile[] files) {
		if (files.length == 0) {
			throw new RuntimeException("Failed to store empty file.");
		}
		log.info("# 채팅방 업로드 POST");
		List<String> uploadedFilesInfo = new ArrayList<>();
		Path fileStorageLocation = Paths.get("src/main/resources/message/").toAbsolutePath();

		int numberOfThreads = files.length;
		if (files.length > Runtime.getRuntime().availableProcessors())
			numberOfThreads = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
		List<Future<String>> futures = new ArrayList<>();

		for (MultipartFile file : files) {
			Callable<String> fileSaveJob = () -> {
				String originalFileName = file.getOriginalFilename();
				String storedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
				try {
					Path targetName = fileStorageLocation.resolve(storedFileName);
					Files.copy(file.getInputStream(), targetName, StandardCopyOption.REPLACE_EXISTING);
					return originalFileName + "::" + storedFileName;
				} catch (IOException e) {
					throw new RuntimeException("Failed to store File:" + originalFileName, e);
				}
			};
			futures.add(executor.submit(fileSaveJob));
		}

		for (Future<String> future : futures) {
			try {
				uploadedFilesInfo.add(future.get());
			} catch (Exception e) {
				throw new RuntimeException("Failed to retrieve file upload result", e);
			}
		}
		executor.shutdown();
		return uploadedFilesInfo;
	}

	@GetMapping("/file/download/{filePath}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String filePath) { // downloadFile 메소드는 서버에 저장된 파일을 클라이언트가 다운로드 하게 해줌
		try {
//		File file = new File(uploadDir + File.separator + filePath);// 파일 경로 설정(파일이 저장된 디렉토리 위치랑 요청경로에서 받은 파일 이름)
			Path fileLocation = Paths.get(uploadDir).resolve(filePath);

			if (!Files.exists(fileLocation)) {
				throw new RuntimeException("File not found.");
			}

//		Resource resource = new FileSystemResource(file);  //파일을 Resource 객체로 반환 준비

			Resource resource = new UrlResource(fileLocation.toUri());
			String encodedFileName = URLEncoder.encode(filePath, StandardCharsets.UTF_8); // 파일 이름을 URL 인코딩하여HTTP 헤더에 안전하게 포함 특수문자나 공백이 포함될 경우 문제가 발생하지 않도록 하기 위함
			return ResponseEntity.ok() // HTTP 응답 생성
					.contentType(MediaType.APPLICATION_OCTET_STREAM) // 응답 콘텐츠 바이너리 데이터로 설정
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")//답 헤더에 콘텐츠 디스포지션을 설정하여 파일이 첨부 파일로 다운 파일 이름은 인코딩된 파일 이름을 사용
					.body(resource);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

}
