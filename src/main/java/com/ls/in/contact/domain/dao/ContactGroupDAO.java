package com.ls.in.contact.domain.dao;

import com.ls.in.contact.domain.model.ContactGroup;

import java.util.List;

public interface ContactGroupDAO {
    public List<ContactGroup> findAllByEmpId(Integer id);
}
