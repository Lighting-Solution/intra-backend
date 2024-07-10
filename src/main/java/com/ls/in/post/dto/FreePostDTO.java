package com.ls.in.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreePostDTO {
    private Integer freePostId;
    private Integer empId;
    private String freePostTitle;
    private String freePostContent;
    private LocalDateTime freePostCreateAt;
    private LocalDateTime freePostUpdateAt;
    private Integer freePostGood;
    private Integer freePostHits;
    private String empName;
}
