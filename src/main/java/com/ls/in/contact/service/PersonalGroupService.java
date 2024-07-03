package com.ls.in.contact.service;

import com.ls.in.contact.exception.ContactGroupNotFoundException;

public interface PersonalGroupService {
    public boolean createPersonalGroup(int empId, String groupName) throws ContactGroupNotFoundException;
    public boolean updatePersonalGroup(int groupId, String groupName) throws ContactGroupNotFoundException;
    public boolean deletePersonalGroup(int groupId) throws ContactGroupNotFoundException;
}
