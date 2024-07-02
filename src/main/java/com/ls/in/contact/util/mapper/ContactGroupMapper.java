package com.ls.in.contact.util.mapper;

import com.ls.in.contact.domain.model.ContactGroup;
import com.ls.in.contact.dto.ContactGroupDTO;
import com.ls.in.contact.dto.PersonalContactDTO;
import com.ls.in.contact.dto.PersonalGroupDTO;

public class ContactGroupMapper {
    public static ContactGroupDTO toDTO(ContactGroup contactGroup) {
        if(contactGroup == null) return null;

        ContactGroupDTO contactGroupDTO = new ContactGroupDTO();
        PersonalContactDTO contactDTO = PersonalContactMapper.toDTO(contactGroup.getPersonalContact());
        PersonalGroupDTO personalGroupDTO = PersonalGroupMapper.toDTO(contactGroup.getPersonalGroup());

        contactGroupDTO.setContactGroupId(contactGroup.getContactGroupId());
        contactGroupDTO.setPersonalContact(contactDTO);
        contactGroupDTO.setPersonalGroup(personalGroupDTO);
        return contactGroupDTO;
    }

}
