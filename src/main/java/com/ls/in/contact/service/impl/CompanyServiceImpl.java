package com.ls.in.contact.service.impl;

import com.ls.in.contact.domain.dao.CompanyDAO;
import com.ls.in.contact.domain.model.Company;
import com.ls.in.contact.dto.CompanyDTO;
import com.ls.in.contact.exception.CompanyNotFoundException;
import com.ls.in.contact.service.CompanyService;
import com.ls.in.contact.util.mapper.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {

    private final CompanyDAO companyDAO;

    @Autowired
    public CompanyServiceImpl(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }


    @Override
    public CompanyDTO createCompany(CompanyDTO companyDTO) throws CompanyNotFoundException {
        Company company = CompanyMapper.toEntity(companyDTO);
        Company result = companyDAO.save(company);
        return CompanyMapper.toDto(result);
    }
}
