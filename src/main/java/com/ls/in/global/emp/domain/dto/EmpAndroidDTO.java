package com.ls.in.global.emp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpAndroidDTO {
    private List<EmpDTO> empDTOList;
    private List<PositionDTO> positionDTOList;
    private List<DepartmentDTO> departmentDTOList;
}
