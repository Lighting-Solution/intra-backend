package com.ls.in.contact.domain.dao.impl;

import com.ls.in.contact.domain.dao.ContactGroupDAO;
import com.ls.in.contact.domain.model.ContactGroup;
import com.ls.in.contact.exception.ContactGroupNotFoundException;
import com.ls.in.contact.repository.ContactGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("contactGroupDAO")
public class ContactGroupDAOImpl implements ContactGroupDAO {

    private final ContactGroupRepository contactGroupRepository;

/*    @Override
    public List<ContactGroup> findAllByEmpId(Integer id) {
        List<ContactGroup> result = contactGroupRepository.findAllByEmpId(id);
        return result;
    }*/

    @Autowired
    public ContactGroupDAOImpl(ContactGroupRepository contactGroupRepository) {
        this.contactGroupRepository = contactGroupRepository;
    }


    @Override
    public Page<ContactGroup> getAllPersonalContactByGroup(Pageable pageable, Integer empId, Integer groupId) throws DataAccessException {
        return contactGroupRepository.findAllByEmpIdByGroupId(pageable, empId, groupId);
    }
}
