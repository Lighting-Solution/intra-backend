package com.ls.in.contact.dto;

import com.ls.in.global.emp.domain.dto.EmpDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalGroupDTO {
    private int personalGroupId;
    private int empId;
    private String personalGroupName;
}
