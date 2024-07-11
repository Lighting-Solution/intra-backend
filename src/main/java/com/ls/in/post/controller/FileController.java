package com.ls.in.post.controller;

import com.ls.in.post.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final NoticeService noticeService;

    @Autowired
    public FileController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(value = "noticePostId", required = false) Integer noticePostId) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Failed to store empty file.");
        }

        try {
            String fileDownloadUri = noticeService.saveFile(file, noticePostId != null ? noticeService.getNoticeEntityById(noticePostId).orElse(null) : null);
            return ResponseEntity.ok(fileDownloadUri);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Failed to store file.");
        }
    }

    @GetMapping("/download/{filePath}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filePath) {
        Resource resource = noticeService.loadFile(filePath);

        String encodedFileName = URLEncoder.encode(resource.getFilename(), StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                .body(resource);
    }
}
