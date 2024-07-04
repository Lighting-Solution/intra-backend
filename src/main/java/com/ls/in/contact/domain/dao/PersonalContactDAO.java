package com.ls.in.contact.domain.dao;

import com.ls.in.contact.domain.model.PersonalContact;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonalContactDAO {
    PersonalContact save(PersonalContact personalContact) throws DataAccessException;
    Page<PersonalContact> findAllByEmpId(Pageable pageable, Integer id) throws DataAccessException;
    boolean deleteById(Integer contactId) throws DataAccessException;
}
