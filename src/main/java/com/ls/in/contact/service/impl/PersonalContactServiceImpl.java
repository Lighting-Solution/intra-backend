package com.ls.in.contact.service.impl;

import com.ls.in.contact.domain.dao.PersonalContactDAO;
import com.ls.in.contact.domain.model.Company;
import com.ls.in.contact.domain.model.PersonalContact;
import com.ls.in.contact.dto.CompanyDTO;
import com.ls.in.contact.dto.PersonalContactDTO;
import com.ls.in.contact.exception.PersonalContactNotFoundException;
import com.ls.in.contact.service.CompanyService;
import com.ls.in.contact.service.PersonalContactService;
import com.ls.in.contact.util.mapper.CompanyMapper;
import com.ls.in.contact.util.mapper.PersonalContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("personalContactService")
public class PersonalContactServiceImpl implements PersonalContactService {

    private final PersonalContactDAO personalContactDAO;
    private final CompanyService companyService;

    @Autowired
    public PersonalContactServiceImpl(PersonalContactDAO personalContactDAO, CompanyService companyService) {
        this.personalContactDAO = personalContactDAO;
        this.companyService = companyService;
    }

    @Override
    public boolean createPersonalContact(PersonalContactDTO requestDTO) throws PersonalContactNotFoundException {
        Company company = CompanyMapper.toEntity(requestDTO.getCompany());
        CompanyDTO companyDTO = CompanyMapper.toDto(company);
        CompanyDTO resultDTO = companyService.createCompany(companyDTO);
        requestDTO.setCompany(resultDTO);
        PersonalContact personalContact = PersonalContactMapper.toEntity(requestDTO);
        PersonalContact result = personalContactDAO.save(personalContact);
        return result != null;
    }

    @Override
    public List<PersonalContactDTO> getAllPersonalContact(int empId) throws PersonalContactNotFoundException {
        List<PersonalContact> result = personalContactDAO.findAllByEmpId(empId);
        List<PersonalContactDTO> responseList = new ArrayList<>();
        for(PersonalContact personalContact : result) {
            PersonalContactDTO tempDTO = PersonalContactMapper.toDto(personalContact);
            responseList.add(tempDTO);
        }
        return responseList;
    }

    @Override
    public PersonalContactDTO updatePersonalContact(PersonalContactDTO requestDTO) throws PersonalContactNotFoundException {
        PersonalContact personalContact = PersonalContactMapper.toEntity(requestDTO);
        PersonalContact result = personalContactDAO.save(personalContact);
        return PersonalContactMapper.toDto(result);
    }

    @Override
    public boolean deletePersonalContacts(Map<String, Object> contactIds) throws PersonalContactNotFoundException {
        List<Integer> idList = (List<Integer>) contactIds.get("contactId");
        return personalContactDAO.deleteAll(idList);
    }
}
