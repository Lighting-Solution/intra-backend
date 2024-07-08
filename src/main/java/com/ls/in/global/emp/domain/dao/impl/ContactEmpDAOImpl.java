package com.ls.in.global.emp.domain.dao.impl;

import com.ls.in.contact.dto.ContactFilterDTO;
import com.ls.in.global.emp.domain.dao.ContactEmpDAO;
import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("contactEmpDAO")
public class ContactEmpDAOImpl implements ContactEmpDAO {

    private final EmpRepository empRepository;

    @Autowired
    public ContactEmpDAOImpl(EmpRepository empRepository) {
        this.empRepository = empRepository;
    }

    @Override
    public List<Emp> findAll() throws DataAccessException {
        return empRepository.findAll();
    }

    @Override
    public List<Emp> findAllByDepartment(ContactFilterDTO data) throws DataAccessException {
        return empRepository.search(data);
    }

}
