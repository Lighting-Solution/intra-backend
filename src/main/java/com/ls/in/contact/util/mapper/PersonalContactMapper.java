package com.ls.in.contact.util.mapper;

import com.ls.in.contact.domain.model.PersonalContact;
import com.ls.in.contact.dto.CompanyDTO;
import com.ls.in.contact.dto.PersonalContactDTO;

public class PersonalContactMapper {
    public static PersonalContactDTO toDTO(PersonalContact personalContact) {
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

        CompanyDTO companyDTO = CompanyMapper.toDTO(personalContact.getCompany());
        personalContactDTO.setCompany(companyDTO);

        return personalContactDTO;
    }
}
