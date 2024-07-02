package com.ls.in.global.emp.domain.dao;

import com.ls.in.global.emp.domain.model.Department;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface DepartmentDAO {
    public List<Department> findAll() throws DataAccessException;
    public Department findById(Integer id) throws DataAccessException;
}
