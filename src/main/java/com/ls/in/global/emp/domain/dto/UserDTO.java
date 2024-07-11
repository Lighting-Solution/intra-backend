package com.ls.in.global.emp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private String accountId;
    private String accountPw;
    private Integer empId;
    private Integer positionId;
    private String empName;
    private Integer departmentId;
    private Boolean empAdmin;
    private String departmentName;
    private String empEmail;
}
