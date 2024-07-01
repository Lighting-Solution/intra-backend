package com.ls.in.global.emp.service.impl;

import com.ls.in.global.emp.domain.dao.EmpDAO;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.domain.dto.EmpMapper;
import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.exception.EmpNotFoundException;
import com.ls.in.global.emp.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("empService")
public class EmpServiceImpl implements EmpService {

    private final EmpDAO empDAO;

    @Autowired
    public EmpServiceImpl(EmpDAO empDAO) {
        this.empDAO = empDAO;
    }


    @Override
    public List<EmpDTO> getAllEmp() throws EmpNotFoundException {
        List<EmpDTO> returnDTOList = new ArrayList<>();
        List<Emp> resultEmpList = empDAO.findAll();
        for (Emp emp : resultEmpList) {
            EmpDTO tempDTO = EmpMapper.toDto(emp);
            returnDTOList.add(tempDTO);
        }
        return returnDTOList;
    }

    @Override
    public EmpDTO getEmpById(int empId) throws EmpNotFoundException {
        Integer id = Integer.valueOf(empId);
        Emp resultEmp = empDAO.findById(id);
        return EmpMapper.toDto(resultEmp);
    }
}
