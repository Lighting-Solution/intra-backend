package com.ls.in.post.controller;

import com.ls.in.post.dto.NoticePostDTO;
import com.ls.in.post.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public List<NoticePostDTO> getAllNotices() {
        return noticeService.getAllNotices();
    }

    @GetMapping("/{id}")
    public Optional<NoticePostDTO> getNoticeById(@PathVariable Integer id) {
        System.out.println("empid" + id);
        return noticeService.getNoticeById(id);
    }

    @PostMapping("/hits/{id}")
    public void incrementNoticeHits(@PathVariable Integer id) {

        noticeService.incrementNoticeHits(id);
    }

    @PostMapping("/create")
    public String createNotice(
            @RequestPart("noticePostDTO") NoticePostDTO noticePostDTO,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            noticeService.createNotice(noticePostDTO, file);
            return "Notice created";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
    @PutMapping("/update/{id}")
    public String updateNotice(
            @PathVariable Integer id,
            @RequestPart("noticePostDTO") NoticePostDTO noticePostDTO,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            noticeService.updateNotice(id, noticePostDTO, file);
            return "Notice updated";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteNotice(@PathVariable Integer id) {
        try {
            noticeService.deleteNotice(id);
            return "Notice deleted";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @PostMapping("/good/{id}")
    public void incrementLike(@PathVariable Integer id) {
        noticeService.incrementNoticeGood(id); // 좋아요 증가
    }
}
