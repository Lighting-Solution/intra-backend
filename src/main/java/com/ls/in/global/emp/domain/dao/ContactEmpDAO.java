package com.ls.in.global.emp.domain.dao;

import com.ls.in.contact.dto.ContactFilterPageDTO;
import com.ls.in.global.emp.domain.model.Emp;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactEmpDAO {
    Page<Emp> findAll(Pageable pageable) throws DataAccessException;
    Page<Emp> findAllByDepartment(ContactFilterPageDTO data) throws DataAccessException;
}
