package com.ls.in.contact.service;

import com.ls.in.contact.domain.model.ContactGroup;
import com.ls.in.contact.dto.ContactGroupDTO;
import com.ls.in.contact.dto.PersonalContactDTO;
import com.ls.in.contact.exception.ContactGroupNotFoundException;
import com.ls.in.contact.exception.PersonalContactNotFoundException;

import java.util.List;

public interface ContactGroupService {
//    public List<ContactGroupDTO> getAllContact(int empId);
    public List<ContactGroupDTO> getAllPersonalContactByGroup(int empId, int groupId) throws PersonalContactNotFoundException;
}
