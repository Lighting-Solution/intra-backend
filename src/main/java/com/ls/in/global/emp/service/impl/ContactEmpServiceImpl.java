package com.ls.in.global.emp.service.impl;

import com.ls.in.contact.domain.model.PersonalContact;
import com.ls.in.contact.dto.ContactFilterPageDTO;
import com.ls.in.contact.dto.PersonalContactDTO;
import com.ls.in.contact.util.mapper.PersonalContactMapper;
import com.ls.in.global.emp.domain.dao.ContactEmpDAO;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.util.EmpMapper;
import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.exception.EmpNotFoundException;
import com.ls.in.global.emp.service.ContactEmpService;
import com.ls.in.global.util.PageNation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("contactEmpService")
public class ContactEmpServiceImpl implements ContactEmpService {

    private final ContactEmpDAO contactEmpDAO;

    @Autowired
    public ContactEmpServiceImpl(ContactEmpDAO contactEmpDAO) {
        this.contactEmpDAO = contactEmpDAO;
    }

    @Override
    public List<EmpDTO> getAllEmp() throws EmpNotFoundException {
        Page<Emp> result = contactEmpDAO.findAll(PageNation.setPage(0, 10));
        List<EmpDTO> responseDTO = new ArrayList<>();
        for(Emp emp : result) {
            EmpDTO tempDTO = EmpMapper.toDto(emp);
            responseDTO.add(tempDTO);
        }
        return responseDTO;
    }

    @Override
    public List<EmpDTO> getAllEmpByDepartment(int department) throws EmpNotFoundException {
        ContactFilterPageDTO data = new ContactFilterPageDTO();
        data.setDepartmentId(department);
        return getSearchList(data);
    }

    @Override
    public List<EmpDTO> getAllByDepartmentSearch(ContactFilterPageDTO requestDTO) throws EmpNotFoundException {
        return getSearchList(requestDTO);
    }

    private List<EmpDTO> getSearchList(ContactFilterPageDTO requestDTO) {
        Page<Emp> result = contactEmpDAO.findAllByDepartment(requestDTO);
        List<EmpDTO> responseList = new ArrayList<>();
        for(Emp emp : result) {
            EmpDTO tempDTO = EmpMapper.toDto(emp);
            responseList.add(tempDTO);
        }
        return responseList;
    }


}
