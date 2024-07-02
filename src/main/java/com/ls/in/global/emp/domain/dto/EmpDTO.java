package com.ls.in.global.emp.domain.dto;

import com.ls.in.contact.dto.CompanyDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Data
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
    private boolean empAdmin;
    private CompanyDTO company;
    private PositionDTO position;
    private DepartmentDTO department;
}
