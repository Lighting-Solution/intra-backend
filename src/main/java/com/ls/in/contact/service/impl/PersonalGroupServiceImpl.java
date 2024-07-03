package com.ls.in.contact.service.impl;

import com.ls.in.contact.domain.dao.PersonalGroupDAO;
import com.ls.in.contact.domain.model.PersonalGroup;
import com.ls.in.contact.exception.ContactGroupNotFoundException;
import com.ls.in.contact.service.PersonalGroupService;
import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.util.Formats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("personalGroupService")
public class PersonalGroupServiceImpl implements PersonalGroupService {

    private final PersonalGroupDAO personalGroupDAO;

    @Autowired
    public PersonalGroupServiceImpl(PersonalGroupDAO personalGroupDAO) {
        this.personalGroupDAO = personalGroupDAO;
    }

    @Override
    public boolean createPersonalGroup(int empId, String groupName) throws ContactGroupNotFoundException {
        Integer id = Formats.toInteger(empId);
        PersonalGroup personalGroup = PersonalGroup.builder()
                .personalGroupName(groupName)
                .emp(Emp.builder().empId(empId).build())
                .build();
        PersonalGroup result = personalGroupDAO.save(personalGroup);
        return result != null;
    }

    @Override
    public boolean updatePersonalGroup(int groupId, String groupName) throws ContactGroupNotFoundException {
        Integer id = Formats.toInteger(groupId);
        PersonalGroup personalGroup = PersonalGroup.builder()
                .personalGroupId(id)
                .personalGroupName(groupName)
                .build();
        PersonalGroup result = personalGroupDAO.save(personalGroup);
        return result.getPersonalGroupName().equals(groupName);
    }

    @Override
    public boolean deletePersonalGroup(int groupId) throws ContactGroupNotFoundException {
        Integer id = Formats.toInteger(groupId);
        return personalGroupDAO.deleteById(groupId);
    }
}
