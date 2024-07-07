package com.ls.in.document.controller;


import com.ls.in.document.dto.DocumentDTO;
import com.ls.in.document.dto.DocumentList;
import com.ls.in.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/document")
public class DocumentController {
	private final DocumentService documentService;
	@PostMapping("/api/publicDocs")
	public List<DocumentList> getPublicDocs(@RequestBody DocumentDTO documentDTO) {
		return null;
	}
}
