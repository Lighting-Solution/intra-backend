package com.ls.in.post.service;

import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.repository.EmpRepository;
import com.ls.in.post.domain.model.FreePost;
import com.ls.in.post.domain.model.NoticePost;
import com.ls.in.post.dto.FreePostDTO;
import com.ls.in.post.repository.FreePostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FreePostService {

    private final FreePostRepository freePostRepository;
    private final EmpRepository empRepository;

    @Autowired
    public FreePostService(FreePostRepository freePostRepository, EmpRepository empRepository) {
        this.freePostRepository = freePostRepository;
        this.empRepository = empRepository;
    }
    public Emp findEmpByAccountIdAndPw(String accountId, String accountPw) {
        return empRepository.findByAccountIdAndAccountPw(accountId, accountPw);
    }

    public List<FreePostDTO> getAllPosts() {
        return freePostRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<FreePostDTO> getPostById(Integer id) {
        return freePostRepository.findById(id).map(this::convertToDTO);
    }

    public FreePost createPost(FreePostDTO freePostDTO, String accountId, String accountPw) {
        Emp emp = empRepository.findByAccountId(accountId);
        if (emp == null) {
            throw new RuntimeException("User not found");
        }
        FreePost freePost = convertToEntity(freePostDTO);
        freePost.setEmp(emp);
        freePost.setFreePostCreateAt(LocalDateTime.now());
        freePost.setFreePostUpdateAt(LocalDateTime.now());
        return freePostRepository.save(freePost);
    }

    public FreePost updatePost(Integer id, FreePostDTO freePostDTO, String accountId, String accountPw) {
        FreePost freePost = freePostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!freePost.getEmp().getAccountId().equals(accountId)) {
            throw new RuntimeException("You can only edit your own posts");
        }

        if (freePostDTO.getFreePostTitle() != null) {
            freePost.setFreePostTitle(freePostDTO.getFreePostTitle());
        }
        if (freePostDTO.getFreePostContent() != null) {
            freePost.setFreePostContent(freePostDTO.getFreePostContent());
        }
        freePost.setFreePostUpdateAt(LocalDateTime.now());
        return freePostRepository.save(freePost);
    }

    public void deletePost(Integer id, String accountId, String accountPw) {
        FreePost freePost = freePostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!freePost.getEmp().getAccountId().equals(accountId)) {
            throw new RuntimeException("You can only delete your own posts");
        }
        freePostRepository.delete(freePost);
    }


    public void incrementFreePostHits(Integer id) {
        Optional<FreePost> freePostOpt = freePostRepository.findById(id);
        if (freePostOpt.isPresent()) {
            FreePost freePost = freePostOpt.get();
            freePost.setFreePostHits(Optional.ofNullable(freePost.getFreePostHits()).orElse(0) + 1);
            freePostRepository.save(freePost);
        }
    }
    public void incrementPostGood(Integer id) {
        Optional<FreePost> freePostOpt = freePostRepository.findById(id);
        if (freePostOpt.isPresent()) {
            FreePost freePost = freePostOpt.get();
            freePost.setFreePostGood(Optional.ofNullable(freePost.getFreePostGood()).orElse(0) + 1);
            freePostRepository.save(freePost);
        }
    }

    private FreePostDTO convertToDTO(FreePost freePost) {
        return new FreePostDTO(
                freePost.getFreePostId(),
                freePost.getEmp().getEmpId(),
                freePost.getFreePostTitle(),
                freePost.getFreePostContent(),
                freePost.getFreePostCreateAt(),
                freePost.getFreePostUpdateAt(),
                freePost.getFreePostGood(),
                freePost.getFreePostHits(),
                freePost.getEmp().getEmpName()
        );
    }

    private FreePost convertToEntity(FreePostDTO freePostDTO) {
        FreePost freePost = new FreePost();
        freePost.setFreePostId(freePostDTO.getFreePostId());
        freePost.setFreePostTitle(freePostDTO.getFreePostTitle());
        freePost.setFreePostContent(freePostDTO.getFreePostContent());
        freePost.setFreePostCreateAt(freePostDTO.getFreePostCreateAt());
        freePost.setFreePostUpdateAt(freePostDTO.getFreePostUpdateAt());
        freePost.setFreePostGood(freePostDTO.getFreePostGood());
        freePost.setFreePostHits(freePostDTO.getFreePostHits());
        Emp emp = new Emp();
        emp.setEmpId(freePostDTO.getEmpId());
        freePost.setEmp(emp);
        return freePost;
    }
}
