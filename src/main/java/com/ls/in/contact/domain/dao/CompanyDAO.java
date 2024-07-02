package com.ls.in.contact.domain.dao;

import com.ls.in.contact.domain.model.Company;
import org.springframework.dao.DataAccessException;

public interface CompanyDAO {
    public Company save(Company company) throws DataAccessException;
    public boolean deleteById(Integer id) throws DataAccessException;
}
