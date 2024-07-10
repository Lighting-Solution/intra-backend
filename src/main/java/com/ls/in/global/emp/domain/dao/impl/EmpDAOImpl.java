package com.ls.in.global.emp.domain.dao.impl;

import com.ls.in.global.emp.domain.dao.EmpDAO;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("empDAO")
public class EmpDAOImpl implements EmpDAO {

    private final EmpRepository empRepository;

    @Autowired
    public EmpDAOImpl(EmpRepository empRepository) {
        this.empRepository = empRepository;
    }


    @Override
    public List<Emp> findAll() throws DataAccessException {
        return empRepository.findAll();
    }

    @Override
    public Emp findById(Integer id) throws DataAccessException {
        Optional<Emp> result = empRepository.findById(id);
        if(result.isEmpty()) return null;
        return result.get();
    }

    @Override
    public Emp findByIdAndDepartmentAndPosition(Integer empId, Integer positionId) throws DataAccessException {
        return empRepository.findByEmpIdAndDepartmentAndPosition(empId, positionId);
    }

    @Override
    public Emp findByPosition(Integer positionId) throws DataAccessException {
        return empRepository.findByPosition(positionId);
    }

    @Override
    public List<Emp> findAllByDepartment(Integer departmentId) throws DataAccessException {
        return empRepository.findAllByDepartment(departmentId);
    }

    @Override
    public boolean save(Emp emp) throws DataAccessException {
        try{
            empRepository.save(emp);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Optional<Emp> findByAccountId(String accountId) {
        return empRepository.findByAccountId(accountId);
    }
    
    @Override
    public Emp findByPositionIdAndDepartmentId(int i, Integer departmentId) {
        return empRepository.findByPositionIdAndDepartmentId(i, departmentId);
    }

}
