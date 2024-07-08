package com.ls.in.contact.util.mapper;

import com.ls.in.contact.domain.model.ContactGroup;
import com.ls.in.contact.domain.model.PersonalContact;
import com.ls.in.contact.domain.model.PersonalGroup;
import com.ls.in.contact.dto.ContactGroupDTO;

public class ContactGroupMapper {
    public static ContactGroupDTO toDto(ContactGroup contactGroup) {
        if(contactGroup == null) return null;

        ContactGroupDTO contactGroupDTO = new ContactGroupDTO();

        if(contactGroup.getPersonalContact() != null)
            contactGroupDTO.setPersonalContactId(contactGroup.getPersonalContact().getPersonalContactId());

        if(contactGroup.getPersonalGroup() != null)
            contactGroupDTO.setPersonalGroupId(contactGroup.getPersonalGroup().getPersonalGroupId());

        contactGroupDTO.setContactGroupId(contactGroup.getContactGroupId());
        return contactGroupDTO;
    }

    public static ContactGroup toEntity(ContactGroupDTO contactGroupDTO) {
        if(contactGroupDTO == null) return null;
        return ContactGroup.builder()
                .contactGroupId(contactGroupDTO.getContactGroupId())
                .personalContact(PersonalContact.builder().personalContactId(contactGroupDTO.getPersonalContactId()).build())
                .personalGroup(PersonalGroup.builder().personalGroupId(contactGroupDTO.getPersonalGroupId()).build())
                .build();
    }

}
