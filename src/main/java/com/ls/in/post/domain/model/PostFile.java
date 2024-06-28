package com.ls.in.post.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "postFile")
public class PostFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Integer postFileId;

    @Column(name = "name")
    private String postFileName;

    @Column(name = "path")
    private String postFilePath;

    @ManyToOne
    @JoinColumn(name = "nPost_id")
    private NoticePost noticePost;
}