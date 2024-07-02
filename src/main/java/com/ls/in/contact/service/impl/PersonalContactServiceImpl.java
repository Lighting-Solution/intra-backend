package com.ls.in.contact.service.impl;

import com.ls.in.contact.domain.dao.PersonalContactDAO;
import com.ls.in.contact.domain.model.PersonalContact;
import com.ls.in.contact.dto.ContactRequestDTO;
import com.ls.in.contact.dto.PersonalContactDTO;
import com.ls.in.contact.exception.PersonalContactNotFoundException;
import com.ls.in.contact.service.PersonalContactService;
import com.ls.in.contact.util.mapper.PersonalContactMapper;
import com.ls.in.global.util.PageNation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("personalContactService")
public class PersonalContactServiceImpl implements PersonalContactService {

    private final PersonalContactDAO personalContactDAO;

    @Autowired
    public PersonalContactServiceImpl(PersonalContactDAO personalContactDAO) {
        this.personalContactDAO = personalContactDAO;
    }


    @Override
    public boolean createPersonalContact(ContactRequestDTO requestDTO) throws PersonalContactNotFoundException {
//        PersonalContact personalContact = PersonalContact.builder()
//                .
//        PersonalContact result = personalContactDAO.save()
        return true;
    }

    @Override
    public List<PersonalContactDTO> getAllPersonalContact() throws PersonalContactNotFoundException {
        Page<PersonalContact> result = personalContactDAO.findAll(PageNation.setPage(0,10));
        List<PersonalContactDTO> responseList = new ArrayList<>();
        for(PersonalContact personalContact : result) {
            PersonalContactDTO tempDTO = PersonalContactMapper.toDTO(personalContact);
            responseList.add(tempDTO);
        }
        return responseList;
    }
}
