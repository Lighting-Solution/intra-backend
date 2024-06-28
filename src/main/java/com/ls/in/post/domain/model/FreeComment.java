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
@Table(name = "freeComment")
public class FreeComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="coment_id")
    private Integer commentId;

    @Column(name = "content")
    private String freeCommentContent;

    @Column(name = "created_at")
    private LocalDateTime freeCommentCreateAt;

    @Column(name = "updated_at")
    private LocalDateTime freeCommentUpdatedAt;

    @ManyToOne
    @JoinColumn(name = "fPost_id")
    private FreePost freePost;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Emp emp;
}
