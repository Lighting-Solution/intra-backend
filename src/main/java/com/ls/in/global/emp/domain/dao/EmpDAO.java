package com.ls.in.global.emp.domain.dao;

import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.domain.model.Emp;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;

public interface EmpDAO {
    List<Emp> findAll() throws DataAccessException;
    Emp findById(Integer id) throws DataAccessException;
    Emp findByIdAndDepartmentAndPosition(Integer empId, Integer positionId) throws DataAccessException;
    Emp findByPosition(Integer positionId) throws DataAccessException;
    List<Emp> findAllByDepartment(Integer departmentId) throws DataAccessException;
    boolean save(Emp emp) throws DataAccessException;

    Optional<Emp> findByAccountId(String accountId);

    Emp findByPositionIdAndDepartmentId(int i, Integer departmentId);

}
