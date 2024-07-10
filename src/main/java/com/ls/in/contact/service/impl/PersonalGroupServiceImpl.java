package com.ls.in.contact.service.impl;

import com.ls.in.contact.domain.dao.PersonalGroupDAO;
import com.ls.in.contact.domain.model.PersonalGroup;
import com.ls.in.contact.dto.PersonalGroupDTO;
import com.ls.in.contact.exception.ContactGroupNotFoundException;
import com.ls.in.contact.exception.PersonalGroupNotFoundException;
import com.ls.in.contact.service.PersonalGroupService;
import com.ls.in.contact.util.mapper.PersonalGroupMapper;
import com.ls.in.global.emp.domain.model.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("personalGroupService")
public class PersonalGroupServiceImpl implements PersonalGroupService {

    private final PersonalGroupDAO personalGroupDAO;

    @Autowired
    public PersonalGroupServiceImpl(PersonalGroupDAO personalGroupDAO) {
        this.personalGroupDAO = personalGroupDAO;
    }

    @Override
    public boolean createPersonalGroup(int empId, String groupName) throws ContactGroupNotFoundException {
        PersonalGroup personalGroup = PersonalGroup.builder()
                .personalGroupName(groupName)
                .emp(Emp.builder().empId(empId).build())
                .build();
        PersonalGroup result = personalGroupDAO.save(personalGroup);
        return result != null;
    }

    @Override
    public boolean updatePersonalGroup(int groupId, String groupName) throws ContactGroupNotFoundException {
        PersonalGroup personalGroup = PersonalGroup.builder()
                .personalGroupId(groupId)
                .personalGroupName(groupName)
                .build();
        PersonalGroup result = personalGroupDAO.save(personalGroup);
        return result.getPersonalGroupName().equals(groupName);
    }

    @Override
    public boolean deletePersonalGroup(int groupId) throws ContactGroupNotFoundException {
        return personalGroupDAO.deleteById(groupId);
    }

    @Override
    public List<PersonalGroupDTO> getAllByEmp(int empId) throws PersonalGroupNotFoundException {
        List<PersonalGroup> result = personalGroupDAO.findAllByEmp(empId);
        List<PersonalGroupDTO> groupDTOList = new ArrayList<>();
        for(PersonalGroup personalGroup : result) {
            PersonalGroupDTO personalGroupDTO = PersonalGroupMapper.toDto(personalGroup);
            groupDTOList.add(personalGroupDTO);
        }
        return groupDTOList;
    }
}
