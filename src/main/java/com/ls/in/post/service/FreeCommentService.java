package com.ls.in.post.service;

import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.repository.EmpRepository;
import com.ls.in.post.domain.model.FreeComment;
import com.ls.in.post.domain.model.FreePost;
import com.ls.in.post.dto.FreeCommentDTO;
import com.ls.in.post.repository.FreeCommentRepository;
import com.ls.in.post.repository.FreePostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FreeCommentService {

    private final FreeCommentRepository freeCommentRepository;
    private final FreePostRepository freePostRepository;
    private final EmpRepository empRepository;

    @Autowired
    public FreeCommentService(FreeCommentRepository freeCommentRepository,
                              FreePostRepository freePostRepository,
                              EmpRepository empRepository) {
        this.freeCommentRepository = freeCommentRepository;
        this.freePostRepository = freePostRepository;
        this.empRepository = empRepository;
    }

    public List<FreeCommentDTO> getCommentsByPostId(Integer freePostId) {
        return freeCommentRepository.findByFreePost_FreePostId(freePostId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public FreeCommentDTO addComment(FreeCommentDTO freeCommentDTO) {
        Optional<FreePost> freePostOpt = freePostRepository.findById(freeCommentDTO.getFreePostId());
        Optional<Emp> empOpt = empRepository.findById(freeCommentDTO.getEmpId());

        if (freePostOpt.isPresent() && empOpt.isPresent()) {
            Emp emp = empOpt.get();
            FreeComment freeComment = new FreeComment();
            freeComment.setFreePost(freePostOpt.get());
            freeComment.setEmp(emp);
            freeComment.setFreeCommentContent(freeCommentDTO.getFreeCommentContent());
            freeComment.setFreeCommentCreateAt(LocalDateTime.now());
            freeComment.setFreeCommentUpdatedAt(LocalDateTime.now());
            freeComment.setFreeCommentGood(0);

            FreeComment savedComment = freeCommentRepository.save(freeComment);
            return convertToDTO(savedComment);
        } else {
            throw new RuntimeException("Post or User not found");
        }
    }

    public FreeCommentDTO updateComment(FreeCommentDTO freeCommentDTO) {
        Optional<FreeComment> freeCommentOpt = freeCommentRepository.findById(freeCommentDTO.getFreeCommentId());
        Optional<Emp> empOpt = empRepository.findById(freeCommentDTO.getEmpId());

        if (freeCommentOpt.isPresent() && empOpt.isPresent()) {
            Emp emp = empOpt.get();
            FreeComment freeComment = freeCommentOpt.get();

            if (!freeComment.getEmp().getEmpId().equals(emp.getEmpId())) {
                throw new RuntimeException("User not authorized to update this comment");
            }

            freeComment.setFreeCommentContent(freeCommentDTO.getFreeCommentContent());
            freeComment.setFreeCommentUpdatedAt(LocalDateTime.now());

            FreeComment updatedComment = freeCommentRepository.save(freeComment);
            return convertToDTO(updatedComment);
        } else {
            throw new RuntimeException("Comment or User not found");
        }
    }

    public void deleteComment(Integer freePostId, Integer commentId, Integer empId) {
        Optional<FreeComment> freeCommentOpt = freeCommentRepository.findById(commentId);
        Optional<Emp> empOpt = empRepository.findById(empId);

        if (freeCommentOpt.isPresent() && empOpt.isPresent()) {
            Emp emp = empOpt.get();
            FreeComment freeComment = freeCommentOpt.get();

            if (!freeComment.getEmp().getEmpId().equals(emp.getEmpId())) {
                throw new RuntimeException("User not authorized to delete this comment");
            }

            freeCommentRepository.delete(freeComment);
        } else {
            throw new RuntimeException("Comment or User not found");
        }
    }

    private FreeCommentDTO convertToDTO(FreeComment freeComment) {
        return new FreeCommentDTO(
                freeComment.getFreeCommentId(),
                freeComment.getFreePost().getFreePostId(),
                freeComment.getEmp().getEmpId(),
                freeComment.getEmp().getEmpName(),
                freeComment.getFreeCommentContent(),
                freeComment.getFreeCommentCreateAt(),
                freeComment.getFreeCommentUpdatedAt(),
                freeComment.getFreeCommentGood()
        );
    }
}
