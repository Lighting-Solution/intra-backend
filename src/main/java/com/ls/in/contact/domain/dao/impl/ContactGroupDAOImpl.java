package com.ls.in.contact.domain.dao.impl;

import com.ls.in.contact.domain.dao.ContactGroupDAO;
import com.ls.in.contact.domain.model.ContactGroup;
import com.ls.in.contact.exception.ContactGroupNotFoundException;
import com.ls.in.contact.repository.ContactGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("contactGroupDAO")
public class ContactGroupDAOImpl implements ContactGroupDAO {

    private final ContactGroupRepository contactGroupRepository;

    @Autowired
    public ContactGroupDAOImpl(ContactGroupRepository contactGroupRepository) {
        this.contactGroupRepository = contactGroupRepository;
    }

    @Override
    public List<ContactGroup> findAllByEmpId(Integer id) {
        List<ContactGroup> result = contactGroupRepository.findAllByEmpId(id);
        return result;
    }
}
