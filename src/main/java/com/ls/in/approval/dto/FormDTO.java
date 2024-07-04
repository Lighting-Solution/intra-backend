package com.ls.in.approval.dto;

import com.ls.in.global.emp.domain.dto.DepartmentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormDTO {
    //사원의 이름, 부서

    private String name;
    private String department;
    private LocalDateTime digitalApprovalCreatedAt;
}
