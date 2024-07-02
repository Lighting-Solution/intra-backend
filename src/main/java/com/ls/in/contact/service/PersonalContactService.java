package com.ls.in.contact.service;

import com.ls.in.contact.dto.ContactRequestDTO;
import com.ls.in.contact.dto.PersonalContactDTO;
import com.ls.in.contact.exception.PersonalContactNotFoundException;

import java.util.List;

public interface PersonalContactService {
    public boolean createPersonalContact(ContactRequestDTO requestDTO) throws PersonalContactNotFoundException;
    public List<PersonalContactDTO> getAllPersonalContact() throws PersonalContactNotFoundException;
}
