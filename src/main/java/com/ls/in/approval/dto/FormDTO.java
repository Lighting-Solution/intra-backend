package com.ls.in.approval.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormDTO {
    // 사원 이름
    private String name;

    // 사원 부서
    private String department;

    // 기안 날짜
    private LocalDateTime digitalApprovalCreatedAt;
}
