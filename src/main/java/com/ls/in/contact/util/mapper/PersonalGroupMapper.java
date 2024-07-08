package com.ls.in.contact.util.mapper;

import com.ls.in.contact.domain.model.PersonalGroup;
import com.ls.in.contact.dto.PersonalGroupDTO;
import com.ls.in.global.emp.domain.model.Emp;

public class PersonalGroupMapper {
    public static PersonalGroupDTO toDto(PersonalGroup personalGroup) {
        if(personalGroup == null) return null;
        PersonalGroupDTO personalGroupDTO = new PersonalGroupDTO();
        personalGroupDTO.setPersonalGroupId(personalGroup.getPersonalGroupId());
        personalGroupDTO.setPersonalGroupName(personalGroup.getPersonalGroupName());

        if(personalGroup.getEmp() != null)
            personalGroupDTO.setEmpId(personalGroup.getEmp().getEmpId());
        return personalGroupDTO;
    }

    public static PersonalGroup toEntity(PersonalGroupDTO personalGroupDTO) {
        if(personalGroupDTO == null) return null;
        return PersonalGroup.builder()
                .personalGroupId(personalGroupDTO.getPersonalGroupId())
                .personalGroupName(personalGroupDTO.getPersonalGroupName())
                .emp(Emp.builder().empId(personalGroupDTO.getEmpId()).build())
                .build();
    }
}
