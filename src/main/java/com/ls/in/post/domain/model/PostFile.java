package com.ls.in.post.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "post_file")
public class PostFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Integer fileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_post_id")
    private NoticePost noticePost;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;


}