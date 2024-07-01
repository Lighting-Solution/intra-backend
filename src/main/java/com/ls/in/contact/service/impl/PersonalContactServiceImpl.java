package com.ls.in.contact.service.impl;

import com.ls.in.contact.domain.dao.PersonalContactDAO;
import com.ls.in.contact.domain.model.PersonalContact;
import com.ls.in.contact.dto.ContactRequestDTO;
import com.ls.in.contact.service.PersonalContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("personalContactService")
public class PersonalContactServiceImpl implements PersonalContactService {

    private final PersonalContactDAO personalContactDAO;

    @Autowired
    public PersonalContactServiceImpl(PersonalContactDAO personalContactDAO) {
        this.personalContactDAO = personalContactDAO;
    }


    @Override
    public boolean createPersonalContact(ContactRequestDTO requestDTO) {
//        PersonalContact personalContact = PersonalContact.builder()
//                .
//        PersonalContact result = personalContactDAO.save()
        return true;
    }
}
