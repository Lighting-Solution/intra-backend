package com.ls.in.contact.domain.dao;

import com.ls.in.contact.domain.model.ContactGroup;
import com.ls.in.contact.exception.ContactGroupNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactGroupDAO {
//    public List<ContactGroup> findAllByEmpId(Integer id);
    public Page<ContactGroup> findAllPersonalContactByGroup(Pageable pageable, Integer empId, Integer groupId) throws DataAccessException;
}
