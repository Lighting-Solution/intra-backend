package com.ls.in.global.emp.service;

import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.exception.EmpNotFoundException;

import java.util.List;

public interface ContactEmpService {
    public List<EmpDTO> getAllEmp() throws EmpNotFoundException;
    public List<EmpDTO> getAllEmpByDepartment(int department) throws EmpNotFoundException;

}
