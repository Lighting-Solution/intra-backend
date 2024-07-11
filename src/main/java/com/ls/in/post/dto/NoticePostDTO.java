package com.ls.in.post.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticePostDTO {
    private Integer noticePostId;
    private String noticeTitle;
    private String noticeContent;
    private Boolean importantNotice;
    private LocalDateTime noticeCreatedAt;
    private LocalDateTime noticeUpdatedAt;
    private Integer noticeHits;
    private Integer noticeGood;


}
