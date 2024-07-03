package com.ls.in.contact.domain.dao;

import com.ls.in.contact.domain.model.PersonalGroup;
import org.springframework.dao.DataAccessException;

public interface PersonalGroupDAO {
    public PersonalGroup save(PersonalGroup personalGroup) throws DataAccessException;
    public boolean deleteById(Integer groupId) throws DataAccessException;
}
