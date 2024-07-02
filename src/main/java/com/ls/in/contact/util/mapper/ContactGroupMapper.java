package com.ls.in.contact.util.mapper;

import com.ls.in.contact.domain.model.ContactGroup;
import com.ls.in.contact.dto.ContactGroupDTO;
import com.ls.in.contact.dto.PersonalContactDTO;
import com.ls.in.contact.dto.PersonalGroupDTO;
import com.ls.in.global.util.Formats;

public class ContactGroupMapper {
    public static ContactGroupDTO toDTO(ContactGroup contactGroup) {
        if(contactGroup == null) return null;

        ContactGroupDTO contactGroupDTO = new ContactGroupDTO();

        if(contactGroup.getPersonalContact() != null)
            contactGroupDTO.setPersonalContact(PersonalContactMapper.toDTO(contactGroup.getPersonalContact()));

        if(contactGroup.getPersonalGroup() != null)
            contactGroupDTO.setPersonalGroup(PersonalGroupMapper.toDTO(contactGroup.getPersonalGroup()));

        contactGroupDTO.setContactGroupId(contactGroup.getContactGroupId());
        return contactGroupDTO;
    }

    public static ContactGroup toEntity(ContactGroupDTO contactGroupDTO) {
        if(contactGroupDTO == null) return null;
        Integer id = Formats.toInteger(contactGroupDTO.getContactGroupId());
        return ContactGroup.builder()
                .contactGroupId(id)
                .personalContact(PersonalContactMapper.toEntity(contactGroupDTO.getPersonalContact()))
                .personalGroup(PersonalGroupMapper.toEntity(contactGroupDTO.getPersonalGroup()))
                .build();
    }

}
