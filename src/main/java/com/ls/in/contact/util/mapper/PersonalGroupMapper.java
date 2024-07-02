package com.ls.in.contact.util.mapper;

import com.ls.in.contact.domain.model.PersonalGroup;
import com.ls.in.contact.dto.PersonalGroupDTO;
import com.ls.in.global.emp.util.EmpMapper;
import com.ls.in.global.util.Formats;

public class PersonalGroupMapper {
    public static PersonalGroupDTO toDTO(PersonalGroup personalGroup) {
        if(personalGroup == null) return null;
        PersonalGroupDTO personalGroupDTO = new PersonalGroupDTO();
        personalGroupDTO.setPersonalGroupId(personalGroup.getPersonalGroupId());
        personalGroupDTO.setPersonalGroupName(personalGroup.getPersonalGroupName());

        if(personalGroup.getEmp() != null)
            personalGroupDTO.setEmp(EmpMapper.toDto(personalGroup.getEmp()));
        return personalGroupDTO;
    }

    public static PersonalGroup toEntity(PersonalGroupDTO personalGroupDTO) {
        if(personalGroupDTO == null) return null;
        Integer id = Formats.toInteger(personalGroupDTO.getPersonalGroupId());
        return PersonalGroup.builder()
                .personalGroupId(id)
                .personalGroupName(personalGroupDTO.getPersonalGroupName())
                .emp(EmpMapper.toEntity(personalGroupDTO.getEmp()))
                .build();
    }
}
