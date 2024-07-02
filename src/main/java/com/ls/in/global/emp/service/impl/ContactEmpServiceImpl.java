package com.ls.in.global.emp.service.impl;

import com.ls.in.global.emp.domain.dao.ContactEmpDAO;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.util.EmpMapper;
import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.exception.EmpNotFoundException;
import com.ls.in.global.emp.service.ContactEmpService;
import com.ls.in.global.util.Formats;
import com.ls.in.global.util.PageNation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("contactEmpService")
public class ContactEmpServiceImpl implements ContactEmpService {

    private final ContactEmpDAO contactEmpDAO;

    @Autowired
    public ContactEmpServiceImpl(ContactEmpDAO contactEmpDAO) {
        this.contactEmpDAO = contactEmpDAO;
    }

    @Override
    public List<EmpDTO> getAllEmp() throws EmpNotFoundException {
        Page<Emp> result = contactEmpDAO.findAll(PageNation.setPage(0, 10));
        List<EmpDTO> responseDTO = new ArrayList<>();
        for(Emp emp : result) {
            System.out.println("테스트 : " + emp.toString());
            EmpDTO tempDTO = EmpMapper.toDto(emp);
            responseDTO.add(tempDTO);
        }
        return responseDTO;
    }

    @Override
    public List<EmpDTO> getAllEmpByDepartment(int department) throws EmpNotFoundException {
        Integer departmentId = Formats.toInteger(department);
        Page<Emp> result = contactEmpDAO.findByDepartmentId( PageNation.setPage(0, 10), departmentId);
        List<EmpDTO> responseDTO = new ArrayList<>();
        for(Emp emp : result) {
            EmpDTO tempDTO = EmpMapper.toDto(emp);
            responseDTO.add(tempDTO);
        }
        return responseDTO;
    }


}
