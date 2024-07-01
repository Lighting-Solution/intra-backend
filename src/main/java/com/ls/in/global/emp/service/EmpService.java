package com.ls.in.global.emp.service;


import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.exception.EmpNotFoundException;

import java.util.List;

public interface EmpService {
    public List<EmpDTO> getAllEmp() throws EmpNotFoundException;
    public EmpDTO getEmpById(int empId) throws EmpNotFoundException;
}
