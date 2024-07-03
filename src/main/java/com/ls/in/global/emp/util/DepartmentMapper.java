package com.ls.in.global.emp.util;

import com.ls.in.global.emp.domain.dto.DepartmentDTO;
import com.ls.in.global.emp.domain.model.Department;
import com.ls.in.global.util.Formats;

public class DepartmentMapper {
    public static DepartmentDTO toDTO(Department department) {
        if(department == null) return null;
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDepartmentId(department.getDepartmentId());
        departmentDTO.setDepartmentName(department.getDepartmentName());
        return departmentDTO;
    }

    public static Department toEntity(DepartmentDTO departmentDTO) {
        if(departmentDTO == null) return null;
        Integer id = Formats.toInteger(departmentDTO.getDepartmentId());
        return Department.builder()
                .departmentId(id)
                .departmentName(departmentDTO.getDepartmentName())
                .build();
    }
}
