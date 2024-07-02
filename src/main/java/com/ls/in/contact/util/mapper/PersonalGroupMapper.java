package com.ls.in.contact.util.mapper;

import com.ls.in.contact.domain.model.PersonalGroup;
import com.ls.in.contact.dto.PersonalGroupDTO;
import com.ls.in.global.emp.util.EmpMapper;

public class PersonalGroupMapper {
    public static PersonalGroupDTO toDTO(PersonalGroup personalGroup) {
        PersonalGroupDTO personalGroupDTO = new PersonalGroupDTO();
        personalGroupDTO.setPersonalGroupId(personalGroup.getPersonalGroupId());
        personalGroupDTO.setPersonalGroupName(personalGroup.getPersonalGroupName());
        personalGroupDTO.setEmp(EmpMapper.toDto(personalGroup.getEmp()));
        return personalGroupDTO;
    }
}
