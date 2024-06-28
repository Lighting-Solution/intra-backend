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
@Table(name = "freePost")
public class FreePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fPost_id")
    private Integer fPostId;

    @Column(name = "title")
    private String freePostTitle;

    @Column(name = "content")
    private String freePostContent;

    @Column(name = "createdAt")
    private LocalDateTime freePostCreatedAt;

    @Column(name = "updatedAt")
    private LocalDateTime freePostUpdatedAt;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Emp emp;
}
