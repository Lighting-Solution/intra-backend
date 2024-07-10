package com.ls.in.contact.domain.dao.impl;

import com.ls.in.contact.domain.dao.PersonalContactDAO;
import com.ls.in.contact.domain.model.PersonalContact;
import com.ls.in.contact.repository.PersonalContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<PersonalContact> findAllByEmpId(Integer id) throws DataAccessException {
        return personalContactRepository.findAllByEmp(id);
    }


    @Override
    public boolean deleteById(Integer contactId) throws DataAccessException {
        try{
            personalContactRepository.deleteById(contactId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteAll(List<Integer> contactIds) throws DataAccessException {
        try{
            personalContactRepository.deleteAllById(contactIds);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
