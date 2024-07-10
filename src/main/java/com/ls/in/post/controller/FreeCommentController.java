package com.ls.in.post.controller;

import com.ls.in.post.dto.FreeCommentDTO;
import com.ls.in.post.service.FreeCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/freeposts/{freePostId}/comments")
public class FreeCommentController {

    private final FreeCommentService freeCommentService;

    @Autowired
    public FreeCommentController(FreeCommentService freeCommentService) {
        this.freeCommentService = freeCommentService;
    }

    @GetMapping
    public List<FreeCommentDTO> getCommentsByPostId(@PathVariable Integer freePostId) {
        return freeCommentService.getCommentsByPostId(freePostId);
    }

    @PostMapping
    public FreeCommentDTO addComment(@PathVariable Integer freePostId, @RequestParam String accountId, @RequestParam String accountPw, @RequestBody FreeCommentDTO freeCommentDTO) {
        freeCommentDTO.setFreePostId(freePostId);
        return freeCommentService.addComment(freeCommentDTO, accountId, accountPw);
    }

    @PutMapping("/{commentId}")
    public FreeCommentDTO updateComment(@PathVariable Integer freePostId, @PathVariable Integer commentId, @RequestParam String accountId, @RequestParam String accountPw, @RequestBody FreeCommentDTO freeCommentDTO) {
        freeCommentDTO.setFreePostId(freePostId);
        freeCommentDTO.setFreeCommentId(commentId);
        return freeCommentService.updateComment(freeCommentDTO, accountId, accountPw);
    }

    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable Integer freePostId, @PathVariable Integer commentId, @RequestParam String accountId, @RequestParam String accountPw) {
        freeCommentService.deleteComment(freePostId, commentId, accountId, accountPw);
        return "Comment deleted";
    }
}
