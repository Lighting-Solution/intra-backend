package com.ls.in.contact.util.mapper;

import com.ls.in.contact.domain.model.Company;
import com.ls.in.contact.dto.CompanyDTO;

public class CompanyMapper {
    public static CompanyDTO toDTO(Company company) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setCompanyId(company.getCompanyId());
        companyDTO.setCompanyName(company.getCompanyName());
        companyDTO.setCompanyAddress(company.getCompanyAddress());
        companyDTO.setCompanyURL(company.getCompanyURL());
        companyDTO.setCompanyNumber(company.getCompanyNumber());
        companyDTO.setCompanyFax(company.getCompanyFax());
        return companyDTO;
    }
}
