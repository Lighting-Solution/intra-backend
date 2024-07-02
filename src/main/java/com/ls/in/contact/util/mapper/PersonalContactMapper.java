package com.ls.in.contact.util.mapper;

import com.ls.in.contact.domain.model.PersonalContact;
import com.ls.in.contact.dto.CompanyDTO;
import com.ls.in.contact.dto.PersonalContactDTO;
import com.ls.in.global.emp.util.EmpMapper;
import com.ls.in.global.util.Formats;

public class PersonalContactMapper {
    public static PersonalContactDTO toDTO(PersonalContact personalContact) {
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

        if(personalContact.getCompany() != null)
            personalContactDTO.setCompany(CompanyMapper.toDTO(personalContact.getCompany()));

        return personalContactDTO;
    }

    public static PersonalContact toEntity(PersonalContactDTO personalContactDTO) {
        if(personalContactDTO == null) return null;
        Integer id = Formats.toInteger(personalContactDTO.getPersonalContactId());
        return PersonalContact.builder()
                .personalContactId(id)
                .positionName(personalContactDTO.getPositionName())
                .departmentName(personalContactDTO.getDepartmentName())
                .personalContactName(personalContactDTO.getPersonalContactName())
                .personalContactNickName(personalContactDTO.getPersonalContactNickName())
                .personalContactEmail(personalContactDTO.getPersonalContactEmail())
                .personalContactMP(personalContactDTO.getPersonalContactMP())
                .personalContactMemo(personalContactDTO.getPersonalContactMemo())
                .personalContactBirthday(personalContactDTO.getPersonalContactBirthday())
                .company(CompanyMapper.toEntity(personalContactDTO.getCompany()))
                .emp(EmpMapper.toEntity(personalContactDTO.getEmp()))
                .build();
    }
}
