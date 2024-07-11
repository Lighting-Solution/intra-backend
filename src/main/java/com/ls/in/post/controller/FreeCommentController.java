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
    public FreeCommentDTO addComment(@PathVariable Integer freePostId, @RequestParam Integer empId, @RequestBody FreeCommentDTO freeCommentDTO) {
        freeCommentDTO.setFreePostId(freePostId);
        freeCommentDTO.setEmpId(empId); // Add empId to the DTO
        return freeCommentService.addComment(freeCommentDTO);
    }

    @PutMapping("/{commentId}")
    public FreeCommentDTO updateComment(@PathVariable Integer freePostId, @PathVariable Integer commentId, @RequestParam Integer empId, @RequestBody FreeCommentDTO freeCommentDTO) {
        freeCommentDTO.setFreePostId(freePostId);
        freeCommentDTO.setFreeCommentId(commentId);
        freeCommentDTO.setEmpId(empId); // Add empId to the DTO
        return freeCommentService.updateComment(freeCommentDTO);
    }

    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable Integer freePostId, @PathVariable Integer commentId, @RequestParam Integer empId) {
        freeCommentService.deleteComment(freePostId, commentId, empId);
        return "Comment deleted";
    }
}
