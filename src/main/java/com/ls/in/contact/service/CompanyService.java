package com.ls.in.contact.service;

import com.ls.in.contact.dto.CompanyDTO;
import com.ls.in.contact.exception.CompanyNotFoundException;

public interface CompanyService {
    CompanyDTO createCompany(CompanyDTO companyDTO) throws CompanyNotFoundException;
}
