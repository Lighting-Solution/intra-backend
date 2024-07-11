package com.ls.in.post.controller;

import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.post.dto.FreePostDTO;

import com.ls.in.post.service.FreePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/freeposts")
public class FreePostController {

    private final FreePostService freePostService;

    @Autowired
    public FreePostController(FreePostService freePostService) {
        this.freePostService = freePostService;
    }

    @GetMapping
    public List<FreePostDTO> getAllPosts() {
        return freePostService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Optional<FreePostDTO> getPostById(@PathVariable Integer id) {
        return freePostService.getPostById(id);
    }

    @PostMapping("/create")
    public String createPost(@RequestBody FreePostDTO freePostDTO) {
        try {
            freePostService.createPost(freePostDTO);
            return "Post created";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @PutMapping("/update/{id}")
    public String updatePost(@PathVariable Integer id, @RequestBody FreePostDTO freePostDTO) {
        try {
            freePostService.updatePost(id, freePostDTO);
            return "Post updated";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deletePost(@PathVariable Integer id) {
        try {
            freePostService.deletePost(id);
            return "Post deleted";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @PostMapping("/hits/{id}")
    public void incrementFreePostHits(@PathVariable Integer id) {
        freePostService.incrementFreePostHits(id);
    }

    @PostMapping("/good/{id}")
    public void incrementLike(@PathVariable Integer id) {
        freePostService.incrementPostGood(id); // 좋아요 증가
    }
    @GetMapping("/emp")
    public ResponseEntity<EmpDTO> getWriterInfo(@RequestParam Integer empId) {
        Emp emp = freePostService.findEmpById(empId);
        if (emp != null) {
            EmpDTO empDTO = new EmpDTO(emp.getEmpId(), emp.getEmpName());
            return ResponseEntity.ok(empDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
