package com.ls.in.global.emp.domain.dto;

import com.ls.in.contact.dto.CompanyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpDTO {
    private int empId;
    private String empName;
    private String empEmail;
    private String empMP;
    private String empMemo;
    private String empHP;
    private String empHomeAddress;
    private String empHomeFax;
    private LocalDate empBirthday;
    private String empSign;
    private CompanyDTO company;
    private PositionDTO position;
    private DepartmentDTO department;
    private String accountId;
    private String accountPw;

    public EmpDTO(int empId, String empName) {
        this.empId = empId;
        this.empName = empName;

    }


}
