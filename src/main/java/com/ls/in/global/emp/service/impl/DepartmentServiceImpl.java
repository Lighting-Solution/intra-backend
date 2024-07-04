package com.ls.in.global.emp.service.impl;

import com.ls.in.global.emp.domain.dao.DepartmentDAO;
import com.ls.in.global.emp.domain.dto.DepartmentDTO;
import com.ls.in.global.emp.domain.model.Department;
import com.ls.in.global.emp.exception.DepartmentNotFoundException;
import com.ls.in.global.emp.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentDAO departmentDAO;

    @Autowired
    public DepartmentServiceImpl(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    @Override
    public List<DepartmentDTO> getAllDepartment() throws DepartmentNotFoundException {
        List<DepartmentDTO> returnDTOList = new ArrayList<>();
        List<Department> resultDepartmentList = departmentDAO.findAll();
        for (Department department : resultDepartmentList) {
            DepartmentDTO departmentDTO = new DepartmentDTO(department.getDepartmentId(), department.getDepartmentName());
            returnDTOList.add(departmentDTO);
        }
        return returnDTOList;
    }

    @Override
    public DepartmentDTO getDepartmentById(int departmentId) throws DepartmentNotFoundException {
        Department result = departmentDAO.findById(departmentId);
        return new DepartmentDTO(result.getDepartmentId(), result.getDepartmentName());
    }
}
