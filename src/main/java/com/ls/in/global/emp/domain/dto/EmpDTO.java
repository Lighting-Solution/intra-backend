package com.ls.in.global.emp.domain.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmpDTO {
    private Integer empId;
    private String empName;
    private String accountId;
    private String accountPw;
}
