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
@Table(name = "free_post")
public class FreePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "free_post_id")
    private Integer freePostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id",nullable = false)
    private Emp emp;

    @Column(name = "free_post_title")
    private String freePostTitle;

    @Column(name = "free_post_content")
    private String freePostContent;

    @Column(name = "free_post_created_at")
    private LocalDateTime freePostCreateAt;

    @Column(name = "free_post_updated_at")
    private LocalDateTime freePostUpdateAt;


    @Column(name = "free_post_good")
    private Integer freePostGood;

    @Column(name = "free_post_hits")
    private Integer freePostHits;

}
