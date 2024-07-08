package com.ls.in.contact.dto;

import com.ls.in.global.emp.domain.dto.DepartmentDTO;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponseDTO {
    private List<EmpDTO> empList;
    private List<PersonalContactDTO> personalContactList;
    private List<DepartmentDTO> departmentDTOList;
    private List<PersonalGroupDTO> groupDTOList;
}
