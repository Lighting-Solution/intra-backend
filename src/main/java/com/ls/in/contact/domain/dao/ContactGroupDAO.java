package com.ls.in.contact.domain.dao;

import com.ls.in.contact.domain.model.ContactGroup;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactGroupDAO {
//    public List<ContactGroup> findAllByEmpId(Integer id);
    public Page<ContactGroup> getAllPersonalContactByGroup(Pageable pageable, Integer empId, Integer groupId) throws DataAccessException;
}
