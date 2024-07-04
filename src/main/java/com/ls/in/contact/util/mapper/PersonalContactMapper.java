package com.ls.in.contact.util.mapper;

import com.ls.in.contact.domain.model.PersonalContact;
import com.ls.in.contact.dto.PersonalContactDTO;
import com.ls.in.global.emp.domain.model.Emp;

public class PersonalContactMapper {
    public static PersonalContactDTO toDto(PersonalContact personalContact) {
        if(personalContact == null) return null;
        PersonalContactDTO personalContactDTO = new PersonalContactDTO();

        personalContactDTO.setPersonalContactId(personalContact.getPersonalContactId());
        personalContactDTO.setPositionName(personalContact.getPositionName());
        personalContactDTO.setDepartmentName(personalContact.getDepartmentName());
        personalContactDTO.setPersonalContactName(personalContact.getPersonalContactName());
        personalContactDTO.setPersonalContactNickName(personalContact.getPersonalContactNickName());
        personalContactDTO.setPersonalContactEmail(personalContact.getPersonalContactEmail());
        personalContactDTO.setPersonalContactMP(personalContact.getPersonalContactMP());
        personalContactDTO.setPersonalContactMemo(personalContact.getPersonalContactMemo());
        personalContactDTO.setPersonalContactBirthday(personalContact.getPersonalContactBirthday());
        personalContactDTO.setEmpId(personalContact.getEmp().getEmpId());
        if(personalContact.getCompany() != null)
            personalContactDTO.setCompany(CompanyMapper.toDto(personalContact.getCompany()));

        return personalContactDTO;
    }

    public static PersonalContact toEntity(PersonalContactDTO personalContactDTO) {
        if(personalContactDTO == null) return null;
        return PersonalContact.builder()
                .personalContactId(personalContactDTO.getPersonalContactId())
                .positionName(personalContactDTO.getPositionName())
                .departmentName(personalContactDTO.getDepartmentName())
                .personalContactName(personalContactDTO.getPersonalContactName())
                .personalContactNickName(personalContactDTO.getPersonalContactNickName())
                .personalContactEmail(personalContactDTO.getPersonalContactEmail())
                .personalContactMP(personalContactDTO.getPersonalContactMP())
                .personalContactMemo(personalContactDTO.getPersonalContactMemo())
                .personalContactBirthday(personalContactDTO.getPersonalContactBirthday())
                .company(CompanyMapper.toEntity(personalContactDTO.getCompany()))
                .emp(Emp.builder().empId(personalContactDTO.getEmpId()).build())
                .build();
    }
}
