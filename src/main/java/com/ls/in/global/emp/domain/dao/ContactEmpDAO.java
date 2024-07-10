package com.ls.in.global.emp.domain.dao;

import com.ls.in.contact.dto.ContactFilterDTO;
import com.ls.in.global.emp.domain.model.Emp;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface ContactEmpDAO {
    List<Emp> findAll() throws DataAccessException;
    List<Emp> findAllByDepartment(ContactFilterDTO data) throws DataAccessException;
}
