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
    private List<PersonalGroupDTO> groupDTOList; // 사용자 개인 주소록 그룹
    private List<EmpDTO> empDTOList; // 모든 사원 연락처 1페이지
    private List<DepartmentDTO> departmentDTOList; // 공용 주소록 그룹
}
