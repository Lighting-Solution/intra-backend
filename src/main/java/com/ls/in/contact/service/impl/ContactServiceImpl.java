package com.ls.in.contact.service.impl;

import com.ls.in.contact.dto.ContactResponseDTO;
import com.ls.in.contact.service.ContactService;
import com.ls.in.contact.service.PersonalGroupService;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.service.ContactEmpService;
import com.ls.in.global.emp.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("contactService")
public class ContactServiceImpl implements ContactService {

    private final ContactEmpService contactEmpService;
    private final DepartmentService departmentService;
    private final PersonalGroupService personalGroupService;

    @Autowired
    public ContactServiceImpl(ContactEmpService contactEmpService, DepartmentService departmentService, PersonalGroupService personalGroupService) {
        this.contactEmpService = contactEmpService;
        this.departmentService = departmentService;
        this.personalGroupService = personalGroupService;
    }


    @Override
    public ContactResponseDTO getAll() {
        List<EmpDTO> empDTOList = contactEmpService.getAllEmp();
        return null;
    }
}
