package com.ls.in.contact.util.mapper;

import com.ls.in.contact.domain.model.Company;
import com.ls.in.contact.dto.CompanyDTO;
import com.ls.in.global.util.Formats;

public class CompanyMapper {
    public static CompanyDTO toDTO(Company company) {
        if(company == null) return null;
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setCompanyId(company.getCompanyId());
        companyDTO.setCompanyName(company.getCompanyName());
        companyDTO.setCompanyAddress(company.getCompanyAddress());
        companyDTO.setCompanyURL(company.getCompanyURL());
        companyDTO.setCompanyNumber(company.getCompanyNumber());
        companyDTO.setCompanyFax(company.getCompanyFax());
        return companyDTO;
    }

    public static Company toEntity(CompanyDTO companyDTO) {
        if(companyDTO == null) return null;
        Integer id = Formats.toInteger(companyDTO.getCompanyId());
        return Company.builder()
                .companyId(id)
                .companyName(companyDTO.getCompanyName())
                .companyAddress(companyDTO.getCompanyAddress())
                .companyURL(companyDTO.getCompanyURL())
                .companyNumber(companyDTO.getCompanyNumber())
                .companyFax(companyDTO.getCompanyFax())
                .build();
    }
}
