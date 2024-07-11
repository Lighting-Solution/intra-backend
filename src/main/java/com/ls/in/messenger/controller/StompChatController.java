package com.ls.in.messenger.controller;

import com.ls.in.messenger.dto.ChatMessageDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface StompChatController {
	/**
	 * description: enter메서드는 초대했을 때 동작하는 메서드다.
	 * 초대 버튼을 눌렀을 때 사원 아이디의 list가 전달된다
	 *
	 * @param message
	 */
	public void enter(ChatMessageDTO message);

	public void message(ChatMessageDTO message);

	public String handleFileUpload(@RequestParam("file") MultipartFile file);

	public ResponseEntity<Resource> downloadFile(@PathVariable String filePath);
}
