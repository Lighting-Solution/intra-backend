package com.ls.in.contact.domain.dao;

import com.ls.in.contact.domain.model.Company;
import org.springframework.dao.DataAccessException;

public interface CompanyDAO {
    Company save(Company company) throws DataAccessException;
    boolean deleteById(Integer id) throws DataAccessException;
}
