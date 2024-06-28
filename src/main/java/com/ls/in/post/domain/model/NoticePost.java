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
@Table(name = "noticePost")
public class NoticePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nPost_id")
    private Integer nPost_id;

    @Column(name = "title")
    private String noticePostTitle;

    @Column(name = "content")
    private String noticePostContent;

    @Column(name = "importantNotice")
    private Boolean importantNotice;

    @Column(name = "createdAt")
    private LocalDateTime noticePostCreatedAt;

    @Column(name = "updatedAt")
    private LocalDateTime noticePostUpdatedAt;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Emp emp;
}
