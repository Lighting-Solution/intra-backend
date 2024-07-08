package com.ls.in.global.emp.domain.dao;

import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.domain.model.Emp;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;

public interface EmpDAO {
    public List<Emp> findAll() throws DataAccessException;
    public Emp findById(Integer id) throws DataAccessException;
    public Emp findByIdAndDepartmentAndPosition(Integer empId, Integer positionId) throws DataAccessException;
    public Emp findByPosition(Integer positionId) throws DataAccessException;

    public Optional<Emp> findByAccountId(String accountId);
}
