package com.ls.in.contact.domain.dao;

import com.ls.in.contact.domain.model.PersonalContact;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonalContactDAO {
    public PersonalContact save(PersonalContact personalContact) throws DataAccessException;
    public Page<PersonalContact> findAll(Pageable pageable) throws DataAccessException;
}
