package com.ls.in.contact.service;

import com.ls.in.contact.dto.PersonalGroupDTO;
import com.ls.in.contact.exception.ContactGroupNotFoundException;
import com.ls.in.contact.exception.PersonalGroupNotFoundException;

import java.util.List;

public interface PersonalGroupService {
    boolean createPersonalGroup(int empId, String groupName) throws ContactGroupNotFoundException;
    boolean updatePersonalGroup(int groupId, String groupName) throws ContactGroupNotFoundException;
    boolean deletePersonalGroup(int groupId) throws ContactGroupNotFoundException;
    List<PersonalGroupDTO> getAllByEmp(int empId) throws PersonalGroupNotFoundException;
}
