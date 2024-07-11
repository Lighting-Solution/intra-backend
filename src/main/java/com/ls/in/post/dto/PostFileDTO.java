package com.ls.in.post.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostFileDTO {
    private Integer fileId;
    private String fileName;
    private String filePath;

}
