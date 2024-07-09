package com.ls.in.contact.service;

import com.ls.in.contact.dto.PersonalContactDTO;
import com.ls.in.contact.exception.PersonalContactNotFoundException;

import java.util.List;

public interface PersonalContactService {
    boolean createPersonalContact(PersonalContactDTO requestDTO) throws PersonalContactNotFoundException;
    List<PersonalContactDTO> getAllPersonalContact(int empId) throws PersonalContactNotFoundException;
    PersonalContactDTO updatePersonalContact(PersonalContactDTO requestDTO) throws PersonalContactNotFoundException;
    boolean deletePersonalContact(int contactId) throws PersonalContactNotFoundException;
}
