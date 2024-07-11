package com.ls.in.contact.service;

import com.ls.in.contact.dto.PersonalContactDTO;
import com.ls.in.contact.exception.PersonalContactNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface PersonalContactService {
    boolean createPersonalContact(PersonalContactDTO requestDTO) throws PersonalContactNotFoundException;
    List<PersonalContactDTO> getAllPersonalContact(int empId) throws PersonalContactNotFoundException;
    PersonalContactDTO updatePersonalContact(PersonalContactDTO requestDTO) throws PersonalContactNotFoundException;
    boolean deletePersonalContacts(Map<String, Object> contactIds) throws PersonalContactNotFoundException;
}
