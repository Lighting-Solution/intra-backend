package com.ls.in.global.emp.service.impl;

import com.ls.in.contact.dto.ContactFilterDTO;
import com.ls.in.global.emp.domain.dao.ContactEmpDAO;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.util.EmpMapper;
import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.exception.EmpNotFoundException;
import com.ls.in.global.emp.service.ContactEmpService;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<Emp> result = contactEmpDAO.findAll();
        List<EmpDTO> responseDTO = new ArrayList<>();
        for(Emp emp : result) {
            EmpDTO tempDTO = EmpMapper.toDto(emp);
            responseDTO.add(tempDTO);
        }
        return responseDTO;
    }

    @Override
    public List<EmpDTO> getAllEmpByDepartment(int department) throws EmpNotFoundException {
        ContactFilterDTO data = new ContactFilterDTO();
        data.setDepartmentId(department);
        return getSearchList(data);
    }

    @Override
    public List<EmpDTO> getAllByDepartmentSearch(ContactFilterDTO requestDTO) throws EmpNotFoundException {
        return getSearchList(requestDTO);
    }

    private List<EmpDTO> getSearchList(ContactFilterDTO requestDTO) {
        List<Emp> result = contactEmpDAO.findAllByDepartment(requestDTO);
        if(result.isEmpty()) return null;
        List<EmpDTO> responseList = new ArrayList<>();
        for(Emp emp : result) {
            EmpDTO tempDTO = EmpMapper.toDto(emp);
            responseList.add(tempDTO);
        }
        return responseList;
    }


}
