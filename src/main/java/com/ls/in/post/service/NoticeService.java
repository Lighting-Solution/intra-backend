package com.ls.in.post.service;

import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.repository.EmpRepository;
import com.ls.in.post.domain.model.NoticePost;
import com.ls.in.post.domain.model.PostFile;
import com.ls.in.post.dto.NoticePostDTO;
import com.ls.in.post.repository.NoticePostRepository;
import com.ls.in.post.repository.PostFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NoticeService {

    private final NoticePostRepository noticePostRepository;
    private final EmpRepository empRepository;
    private final PostFileRepository postFileRepository;
    private final String uploadDir = "C:/intra-backend/post_file"; // 실제 파일 저장 경로 설정

    @Autowired
    public NoticeService(NoticePostRepository noticePostRepository, EmpRepository empRepository, PostFileRepository postFileRepository) {
        this.noticePostRepository = noticePostRepository;
        this.empRepository = empRepository;
        this.postFileRepository = postFileRepository;
    }

    public List<NoticePostDTO> getAllNotices() {
        return noticePostRepository.findAll(Sort.by(Sort.Order.desc("importantNotice"), Sort.Order.desc("noticeCreatedAt"))).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<NoticePostDTO> getNoticeById(Integer id) {
        return noticePostRepository.findById(id).map(this::convertToDTO);
    }

    public Optional<NoticePost> getNoticeEntityById(Integer id) {
        return noticePostRepository.findById(id);
    }

    public NoticePost createNotice(NoticePostDTO noticePostDTO, MultipartFile file) {
        NoticePost noticePost = convertToEntity(noticePostDTO);
        noticePost.setNoticeCreatedAt(LocalDateTime.now());
        noticePost.setNoticeUpdatedAt(LocalDateTime.now());
        noticePost = noticePostRepository.save(noticePost);

        if (file != null && !file.isEmpty()) {
            saveFile(file, noticePost);
        }

        return noticePost;
    }

    public NoticePost updateNotice(Integer id, NoticePostDTO noticePostDTO, MultipartFile file) {
        NoticePost noticePost = noticePostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found"));

        if (noticePostDTO.getNoticeTitle() != null) {
            noticePost.setNoticeTitle(noticePostDTO.getNoticeTitle());
        }
        if (noticePostDTO.getNoticeContent() != null) {
            noticePost.setNoticeContent(noticePostDTO.getNoticeContent());
        }
        if (noticePostDTO.getImportantNotice() != null) {
            noticePost.setImportantNotice(noticePostDTO.getImportantNotice());
        }
        noticePost.setNoticeUpdatedAt(LocalDateTime.now());

        // 기존 조회수와 좋아요 수를 그대로 유지
        noticePost.setNoticeHits(noticePost.getNoticeHits());
        noticePost.setNoticeGood(noticePost.getNoticeGood());

        noticePost = noticePostRepository.save(noticePost);

        // 파일이 있는 경우 처리
        if (file != null && !file.isEmpty()) {
            saveFile(file, noticePost);
        }

        return noticePost;
    }



    public void deleteNotice(Integer id) {
        // 먼저 관련 파일들을 삭제
        List<PostFile> files = postFileRepository.findByNoticePost_NoticePostId(id);
        for (PostFile file : files) {
            File fileToDelete = new File(uploadDir + File.separator + file.getFilePath());
            if (fileToDelete.exists()) {
                fileToDelete.delete();
            }
            postFileRepository.delete(file);
        }
        // 그 후 게시글을 삭제
        noticePostRepository.deleteById(id);
    }
    public void incrementNoticeHits(Integer id) {
        Optional<NoticePost> noticePostOpt = noticePostRepository.findById(id);
        if (noticePostOpt.isPresent()) {
            NoticePost noticePost = noticePostOpt.get();
            noticePost.setNoticeHits(Optional.ofNullable(noticePost.getNoticeHits()).orElse(0) + 1);
            noticePostRepository.save(noticePost);
        }
    }

    public void incrementNoticeGood(Integer id) {
        Optional<NoticePost> noticePostOpt = noticePostRepository.findById(id);
        if (noticePostOpt.isPresent()) {
            NoticePost noticePost = noticePostOpt.get();
            noticePost.setNoticeGood(Optional.ofNullable(noticePost.getNoticeGood()).orElse(0) + 1);
            noticePostRepository.save(noticePost);
        }
    }

    public String saveFile(MultipartFile file, NoticePost noticePost) {
        try {
            String originalFileName = file.getOriginalFilename();
            String storedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
            File destinationFile = new File(uploadDir + File.separator + storedFileName);
            file.transferTo(destinationFile);

            PostFile postFile = new PostFile();
            postFile.setFileName(originalFileName);
            postFile.setFilePath(storedFileName);
            postFile.setNoticePost(noticePost);
            postFileRepository.save(postFile);

            return storedFileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }


    public Resource loadFile(String filePath) {
        File file = new File(uploadDir + File.separator + filePath);

        if (!file.exists()) {
            throw new RuntimeException("File not found.");
        }

        return new FileSystemResource(file);
    }

    private boolean isAdmin(String accountId, String accountPw) {
        Optional<Emp> optionalEmp = empRepository.findByAccountId(accountId);
        if (optionalEmp.isPresent()) {
            Emp emp = optionalEmp.get();
            return emp.isEmpAdmin() && emp.getAccountPw().equals(accountPw);
        } else {
            return false; // 혹은 적절한 예외를 던질 수 있습니다.
        }
    }

    private NoticePostDTO convertToDTO(NoticePost noticePost) {
        return new NoticePostDTO(
                noticePost.getNoticePostId(),
                noticePost.getNoticeTitle(),
                noticePost.getNoticeContent(),
                noticePost.getImportantNotice(),
                noticePost.getNoticeCreatedAt(),
                noticePost.getNoticeUpdatedAt(),
                noticePost.getNoticeHits(),
                noticePost.getNoticeGood()
        );
    }

    private NoticePost convertToEntity(NoticePostDTO noticePostDTO) {
        return new NoticePost(
                noticePostDTO.getNoticePostId(),
                null, // emp 필드를 설정해야 하면 적절하게 설정해야 함
                noticePostDTO.getNoticeTitle(),
                noticePostDTO.getNoticeContent(),
                noticePostDTO.getImportantNotice(),
                noticePostDTO.getNoticeCreatedAt(),
                noticePostDTO.getNoticeUpdatedAt(),
                noticePostDTO.getNoticeHits() == null ? 0 : noticePostDTO.getNoticeHits(),
                noticePostDTO.getNoticeGood() == null ? 0 : noticePostDTO.getNoticeGood()
        );
    }
}
