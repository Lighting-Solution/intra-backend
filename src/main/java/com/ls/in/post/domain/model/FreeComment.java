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
@Table(name = "free_comment")
public class FreeComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="free_comment_id")
    private Integer freeCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_post_id",nullable = false)
    private FreePost freePost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id",nullable = false)
    private Emp emp;

    @Column(name = "free_comment_content")
    private String freeCommentContent;

    @Column(name = "free_comment_created_at")
    private LocalDateTime freeCommentCreateAt;

    @Column(name = "free_comment_updated_at")
    private LocalDateTime freeCommentUpdatedAt;


    @Column(name = "free_comment_good")
    private Integer freeCommentGood= 0;


}
