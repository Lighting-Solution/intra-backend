package com.ls.in.global.emp.service;

import com.ls.in.global.emp.domain.dto.DepartmentDTO;
import com.ls.in.global.emp.exception.DepartmentNotFoundException;

import java.util.List;

public interface DepartmentService {
    public List<DepartmentDTO> getAllDepartment() throws DepartmentNotFoundException;
    public DepartmentDTO getDepartmentById(int departmentId) throws DepartmentNotFoundException;
}
