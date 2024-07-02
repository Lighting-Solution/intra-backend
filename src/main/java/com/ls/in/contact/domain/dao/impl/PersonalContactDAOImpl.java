package com.ls.in.contact.domain.dao.impl;

import com.ls.in.contact.domain.dao.PersonalContactDAO;
import com.ls.in.contact.domain.model.PersonalContact;
import com.ls.in.contact.repository.PersonalContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository("personalContactDAO")
public class PersonalContactDAOImpl implements PersonalContactDAO {

    private final PersonalContactRepository personalContactRepository;

    @Autowired
    public PersonalContactDAOImpl(PersonalContactRepository personalContactRepository) {
        this.personalContactRepository = personalContactRepository;
    }


    @Override
    public PersonalContact save(PersonalContact personalContact) throws DataAccessException {
        return personalContactRepository.save(personalContact);
    }

    @Override
    public Page<PersonalContact> findAll(Pageable pageable) throws DataAccessException {
        return personalContactRepository.findAll(pageable);
    }
}
