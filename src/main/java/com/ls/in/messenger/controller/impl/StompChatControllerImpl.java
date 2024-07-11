package com.ls.in.messenger.controller.impl;

import com.ls.in.messenger.controller.StompChatController;
import com.ls.in.messenger.dto.ChatMessageDTO;
import com.ls.in.messenger.service.impl.MessageServiceImpl;

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
public class StompChatControllerImpl implements StompChatController {
	private final MessageServiceImpl messageService;
	private final SimpMessagingTemplate template;//특정 Broker로 메세지를 전달
	private final String uploadDir = "C:/intra-backend/message_file";
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

	@PostMapping("/file/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file) { // hadnleFileUpload메소드는 클라이언트가 업로드한 파일을 서버에 저장하는 역할
		//MultipartFile 스프링 프레임워크에서 제공하는 인터페이스로, 멀티파트 형식으로 전송된 파일 데이터를 나타냅니다.
		// 이를 통해 업로드된 파일의 내용과 메타데이터(예: 파일 이름, 파일 크기 등)에 접근할 수 있습니다. 라고 지피티가 말합니다
		if (file.isEmpty()) {
			throw new RuntimeException("Failed to store empty file."); // 비어 있으면 예외처리
		}
		log.info("# 채팅방 업로드 POST");
		String originalFileName = file.getOriginalFilename(); // 클라이언트가 업로드한 파일의 원래 이름 가져오기
		String storedFileName = UUID.randomUUID().toString() + "_" + originalFileName; // 중복 방지를 위해 UUID 사용하여 원래 파일 앞에 UUID 추가한 고유한 이름 생성
		File destinationFile = new File(uploadDir + File.separator + storedFileName); // 저장경로 설정 separator는 구분자(\,/) 구분해주는 역할

		try {
			file.transferTo(destinationFile);// 업로드 된 파일을 설정된 경로에 저장 저장중 문제가 발생하면 예외처리
			return originalFileName + "::" + storedFileName;
		} catch (IOException e) {
			throw new RuntimeException("Failed to store file.", e);
		}
	}

	@GetMapping("/file/download/{filePath}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String filePath) { // downloadFile 메소드는 서버에 저장된 파일을 클라이언트가 다운로드 하게 해줌
		File file = new File(uploadDir + File.separator + filePath);// 파일 경로 설정(파일이 저장된 디렉토리 위치랑 요청경로에서 받은 파일 이름)

		if (!file.exists()) {
			throw new RuntimeException("File not found.");
		}

		Resource resource = new FileSystemResource(file);  //파일을 Resource 객체로 반환 준비
		String encodedFileName = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8); // 파일 이름을 URL 인코딩하여HTTP 헤더에 안전하게 포함 특수문자나 공백이 포함될 경우 문제가 발생하지 않도록 하기 위함
		return ResponseEntity.ok() // HTTP 응답 생성
				.contentType(MediaType.APPLICATION_OCTET_STREAM) // 응답 콘텐츠 바이너리 데이터로 설정
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")//답 헤더에 콘텐츠 디스포지션을 설정하여 파일이 첨부 파일로 다운 파일 이름은 인코딩된 파일 이름을 사용
				.body(resource);
	}
}
