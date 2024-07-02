package com.ls.in.contact.domain.dao.impl;

import com.ls.in.contact.domain.dao.CompanyDAO;
import com.ls.in.contact.domain.model.Company;
import com.ls.in.contact.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("companyDAO")
public class CompanyDAOImpl implements CompanyDAO {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyDAOImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company save(Company company) throws DataAccessException {
        Company result = companyRepository.save(company);
        return result;
    }

    @Override
    public boolean deleteById(Integer id) throws DataAccessException {
        companyRepository.deleteById(id);
        Optional<Company> result = companyRepository.findById(id);
        if(result.isPresent()) return false;
        return true;
    }


}


