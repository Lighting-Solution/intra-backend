package com.ls.in.contact.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalContactDTO {
    private int personalContactId;
    private String positionName;
    private String departmentName;
    private String personalContactName;
    private String personalContactNickName;
    private String personalContactEmail;
    private String personalContactMP;
    private String personalContactMemo;
    private LocalDate personalContactBirthday;
    private CompanyDTO company;
    private int empId;
}
