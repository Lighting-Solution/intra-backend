package com.ls.in.contact.domain.dao;

import com.ls.in.contact.domain.model.PersonalContact;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface PersonalContactDAO {
    PersonalContact save(PersonalContact personalContact) throws DataAccessException;
    List<PersonalContact> findAllByEmpId(Integer id) throws DataAccessException;
    boolean deleteById(Integer contactId) throws DataAccessException;
    boolean deleteAll(List<Integer> contactIds) throws DataAccessException;
}
