package com.ls.in.contact.dto;

import com.ls.in.contact.domain.model.ContactGroup;

public class ContactGroupMapper {
    public static ContactGroupDTO toDTO(ContactGroup contactGroup) {
        if(contactGroup == null) return null;

        ContactGroupDTO contactGroupDTO = new ContactGroupDTO();
        PersonalContactDTO contactDTO = pcConvertDTO(contactGroup);
        PersonalGroupDTO personalGroupDTO = pgConvertDTO(contactGroup);

        contactGroupDTO.setContactGroupId(contactGroup.getContactGroupId());
        contactGroupDTO.setPersonalContact(contactDTO);
        contactGroupDTO.setPersonalGroup(personalGroupDTO);
        return contactGroupDTO;
    }

    private static PersonalContactDTO pcConvertDTO(ContactGroup contactGroup) {
        PersonalContactDTO personalContactDTO = new PersonalContactDTO();
        personalContactDTO.setPersonalContactId(contactGroup.getPersonalContact().getPersonalContactId());
        personalContactDTO.setPositionName(contactGroup.getPersonalContact().getPositionName());
        personalContactDTO.setDepartmentName(contactGroup.getPersonalContact().getDepartmentName());
        personalContactDTO.setPersonalContactName(contactGroup.getPersonalContact().getPersonalContactName());
        personalContactDTO.setPersonalContactNickName(contactGroup.getPersonalContact().getPersonalContactNickName());
        personalContactDTO.setPersonalContactEmail(contactGroup.getPersonalContact().getPersonalContactEmail());
        personalContactDTO.setPersonalContactMP(contactGroup.getPersonalContact().getPersonalContactMP());
        personalContactDTO.setPersonalContactMemo(contactGroup.getPersonalContact().getPersonalContactMemo());
        personalContactDTO.setPersonalContactBirthday(contactGroup.getPersonalContact().getPersonalContactBirthday());

        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setCompanyId(contactGroup.getPersonalContact().getCompany().getCompanyId());
        companyDTO.setCompanyName(contactGroup.getPersonalContact().getCompany().getCompanyName());
        companyDTO.setCompanyAddress(contactGroup.getPersonalContact().getCompany().getCompanyAddress());
        companyDTO.setCompanyURL(contactGroup.getPersonalContact().getCompany().getCompanyURL());
        companyDTO.setCompanyNumber(contactGroup.getPersonalContact().getCompany().getCompanyNumber());
        companyDTO.setCompanyFax(contactGroup.getPersonalContact().getCompany().getCompanyFax());

        personalContactDTO.setCompany(companyDTO);

        return personalContactDTO;
    }

    private static PersonalGroupDTO pgConvertDTO(ContactGroup contactGroup) {
        PersonalGroupDTO groupDTO = new PersonalGroupDTO();
        groupDTO.setPersonalGroupId(contactGroup.getPersonalGroup().getPersonalGroupId());
        groupDTO.setPersonalGroupName(contactGroup.getPersonalGroup().getPersonalGroupName());
        return groupDTO;
    }
}
