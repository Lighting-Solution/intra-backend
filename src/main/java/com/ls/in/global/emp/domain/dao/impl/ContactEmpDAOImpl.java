package com.ls.in.global.emp.domain.dao.impl;

import com.ls.in.contact.dto.ContactFilterPageDTO;
import com.ls.in.global.emp.domain.dao.ContactEmpDAO;
import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository("contactEmpDAO")
public class ContactEmpDAOImpl implements ContactEmpDAO {

    private final EmpRepository empRepository;

    @Autowired
    public ContactEmpDAOImpl(EmpRepository empRepository) {
        this.empRepository = empRepository;
    }

    @Override
    public Page<Emp> findAll(Pageable pageable) throws DataAccessException {
        return empRepository.findAll(pageable);
    }

    @Override
    public Page<Emp> findAllByDepartment(ContactFilterPageDTO data) throws DataAccessException {
        return empRepository.search(data);
    }

}
