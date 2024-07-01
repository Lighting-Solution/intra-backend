package com.ls.in.contact.service;

import com.ls.in.contact.dto.ContactGroupDTO;

import java.util.List;

public interface ContactGroupService {
    public List<ContactGroupDTO> getAllContact(int empId);
}
