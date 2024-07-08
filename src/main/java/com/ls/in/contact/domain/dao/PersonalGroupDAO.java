package com.ls.in.contact.domain.dao;

import com.ls.in.contact.domain.model.PersonalGroup;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface PersonalGroupDAO {
    PersonalGroup save(PersonalGroup personalGroup) throws DataAccessException;
    boolean deleteById(Integer groupId) throws DataAccessException;
    List<PersonalGroup> findAllByEmp(Integer empId) throws DataAccessException;
}
