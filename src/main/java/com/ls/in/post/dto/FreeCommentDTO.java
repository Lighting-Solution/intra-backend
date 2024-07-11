package com.ls.in.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreeCommentDTO {
    private Integer freeCommentId;
    private Integer freePostId;
    private Integer empId;
    private String empName;
    private String freeCommentContent;
    private LocalDateTime freeCommentCreateAt;
    private LocalDateTime freeCommentUpdatedAt;
    private Integer freeCommentGood;
}
