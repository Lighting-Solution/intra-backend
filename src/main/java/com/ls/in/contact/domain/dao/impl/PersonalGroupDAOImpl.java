package com.ls.in.contact.domain.dao.impl;

import com.ls.in.contact.domain.dao.PersonalGroupDAO;
import com.ls.in.contact.domain.model.PersonalGroup;
import com.ls.in.contact.repository.PersonalGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("personalGroupDAO")
public class PersonalGroupDAOImpl implements PersonalGroupDAO {

    private final PersonalGroupRepository personalGroupRepository;

    @Autowired
    public PersonalGroupDAOImpl(PersonalGroupRepository personalGroupRepository) {
        this.personalGroupRepository = personalGroupRepository;
    }

    @Override
    public PersonalGroup save(PersonalGroup personalGroup) throws DataAccessException {
        return personalGroupRepository.save(personalGroup);
    }

    @Override
    public boolean deleteById(Integer groupId) throws DataAccessException {
        try{
            personalGroupRepository.deleteById(groupId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<PersonalGroup> findAllByEmp(Integer empId) throws DataAccessException {
        return personalGroupRepository.findAllByEmp(empId);
    }
}
