package com.ls.in.global.emp.domain.dao.impl;

import com.ls.in.global.emp.domain.dao.DepartmentDAO;
import com.ls.in.global.emp.domain.model.Department;
import com.ls.in.global.emp.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("departmentDAO")
public class DepartmentDAOImpl implements DepartmentDAO {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentDAOImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    @Override
    public List<Department> findAll() throws DataAccessException {
        return departmentRepository.findAll();
    }

    @Override
    public Department findById(Integer id) throws DataAccessException {
        Optional<Department> result = departmentRepository.findById(id);
        if(result.isEmpty()) return null;
        return result.get();
    }
}
