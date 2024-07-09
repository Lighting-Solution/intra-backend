package com.ls.in.global.emp.util;

import com.ls.in.global.emp.domain.dto.DepartmentDTO;
import com.ls.in.global.emp.domain.model.Department;

public class DepartmentMapper {
    public static DepartmentDTO toDto(Department department) {
        if(department == null) return null;
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDepartmentId(department.getDepartmentId());
        departmentDTO.setDepartmentName(department.getDepartmentName());
        return departmentDTO;
    }

    public static Department toEntity(DepartmentDTO departmentDTO) {
        if(departmentDTO == null) return null;
        return Department.builder()
                .departmentId(departmentDTO.getDepartmentId())
                .departmentName(departmentDTO.getDepartmentName())
                .build();
    }
}
