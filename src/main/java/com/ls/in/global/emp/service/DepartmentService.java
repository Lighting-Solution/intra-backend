package com.ls.in.global.emp.service;

import com.ls.in.global.emp.domain.dto.DepartmentDTO;
import com.ls.in.global.emp.exception.DepartmentNotFoundException;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDTO> getAllDepartment() throws DepartmentNotFoundException;
    DepartmentDTO getDepartmentById(int departmentId) throws DepartmentNotFoundException;
}
