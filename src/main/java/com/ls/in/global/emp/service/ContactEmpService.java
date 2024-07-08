package com.ls.in.global.emp.service;

import com.ls.in.contact.dto.ContactFilterDTO;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.exception.EmpNotFoundException;

import java.util.List;

public interface ContactEmpService {
    List<EmpDTO> getAllEmp() throws EmpNotFoundException;
    List<EmpDTO> getAllEmpByDepartment(int department) throws EmpNotFoundException;
    List<EmpDTO> getAllByDepartmentSearch(ContactFilterDTO requestDTO) throws EmpNotFoundException;

}
