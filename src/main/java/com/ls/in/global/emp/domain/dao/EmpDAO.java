package com.ls.in.global.emp.domain.dao;

import com.ls.in.global.emp.domain.model.Emp;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface EmpDAO {
    public List<Emp> findAll() throws DataAccessException;
    public Emp findById(Integer id) throws DataAccessException;
}
