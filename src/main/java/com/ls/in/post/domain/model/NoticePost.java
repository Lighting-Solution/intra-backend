package com.ls.in.post.domain.model;

import com.ls.in.global.emp.domain.model.Emp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notice_post")
public class NoticePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_post_id")
    private Integer noticePostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id")
    private Emp emp;

    @Column(name = "notice_title")
    private String noticeTitle;

    @Column(name = "notice_content")
    private String noticeContent;

    @Column(name = "important_notice")
    private Boolean importantNotice;

    @Column(name = "notice_created_at")
    private LocalDateTime noticeCreatedAt;

    @Column(name = "notice_updated_at")
    private LocalDateTime noticeUpdatedAt;

    @Column(name = "notice_hits", nullable = false)
    private Integer noticeHits = 0; // 기본값 설정

    @Column(name = "notice_good", nullable = false)
    private Integer noticeGood = 0; // 기본값 설정


}
