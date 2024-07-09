package com.ls.in.contact.service.impl;

import com.ls.in.contact.dto.*;
import com.ls.in.contact.service.ContactGroupService;
import com.ls.in.contact.service.ContactService;
import com.ls.in.contact.service.PersonalContactService;
import com.ls.in.contact.service.PersonalGroupService;
import com.ls.in.global.emp.domain.dto.DepartmentDTO;
import com.ls.in.global.emp.domain.dto.EmpAndroidDTO;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.service.ContactEmpService;
import com.ls.in.global.emp.service.DepartmentService;
import com.ls.in.global.emp.service.PositionService;
import com.ls.in.global.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("contactService")
public class ContactServiceImpl implements ContactService {

    private final ContactGroupService contactGroupService;
    private final PersonalGroupService personalGroupService;
    private final PersonalContactService personalContactService;
    private final ContactEmpService contactEmpService;
    private final PositionService positionService;
    private final DepartmentService departmentService;

    @Autowired
    public ContactServiceImpl(ContactGroupService contactGroupService, ContactEmpService contactEmpService, DepartmentService departmentService, PersonalGroupService personalGroupService, PersonalContactService personalContactService, PositionService positionService) {
        this.contactGroupService = contactGroupService;
        this.contactEmpService = contactEmpService;
        this.departmentService = departmentService;
        this.personalGroupService = personalGroupService;
        this.personalContactService = personalContactService;
        this.positionService = positionService;
    }


    @Override
    public ContactResponseDTO getAll(int empId) {
        List<EmpDTO> empDTOList = contactEmpService.getAllEmp();
        List<DepartmentDTO> departmentDTOList = departmentService.getAllDepartment();
        List<PersonalGroupDTO> personalGroupDTOList = personalGroupService.getAllByEmp(empId);

        ContactResponseDTO responseDTO = new ContactResponseDTO();
        responseDTO.setEmpList(empDTOList);
        responseDTO.setDepartmentDTOList(departmentDTOList);
        responseDTO.setGroupDTOList(personalGroupDTOList);
        return responseDTO;
    }

    @Override
    public EmpAndroidDTO getEmpALlByAndroid() {
        EmpAndroidDTO empAndroidDTO = new EmpAndroidDTO();
        empAndroidDTO.setEmpDTOList(contactEmpService.getAllEmp());
        empAndroidDTO.setDepartmentDTOList(departmentService.getAllDepartment());
        empAndroidDTO.setPositionDTOList(positionService.getAllPosition());
        return empAndroidDTO;
    }

    @Override
    public ContactAndroidDTO getPersonalAllByAndroid(int empId) {
        ContactAndroidDTO contactAndroidDTO = new ContactAndroidDTO();
        contactAndroidDTO.setPersonalContactDTOList(personalContactService.getAllPersonalContact(empId));
        contactAndroidDTO.setPersonalGroupDTOList(personalGroupService.getAllByEmp(empId));
        contactAndroidDTO.setContactGroupDTOList(contactGroupService.getAllByEmpId(empId));
        return contactAndroidDTO;
    }

    @Override
    public ContactResponseDTO getAllBySearch(ContactFilterDTO requestDTO) {
        if(!(Utils.checkIntegerNull(requestDTO.getDepartmentId()))) {
            List<EmpDTO> resultList = contactEmpService.getAllByDepartmentSearch(requestDTO);
            ContactResponseDTO responseDTO = new ContactResponseDTO();
            if(resultList == null) return null;
            System.out.println(resultList.toString());
            responseDTO.setEmpList(resultList);
            return responseDTO;
        } else if(!(Utils.checkIntegerNull(requestDTO.getGroupId()))) {
            List<PersonalContactDTO> resultList = contactGroupService.getAllByGroupBySearch(requestDTO);
            ContactResponseDTO responseDTO = new ContactResponseDTO();
            if(resultList == null) return null;
            System.out.println(resultList.toString());
            responseDTO.setPersonalContactList(resultList);
            return responseDTO;
        } else if(!(Utils.checkIntegerNull(requestDTO.getEmpId()))) {
            System.out.println("사내 전체 주소록 조회");
            if(requestDTO.getGroupType().equals("company")) {
                List<EmpDTO> resultList = contactEmpService.getAllByDepartmentSearch(requestDTO);
                ContactResponseDTO responseDTO = new ContactResponseDTO();
                if(resultList == null) return null;
                System.out.println(resultList.toString());
                responseDTO.setEmpList(resultList);
                return responseDTO;
            } else if(requestDTO.getGroupType().equals("personal")) {
                System.out.println("개인 전체 주소록 조회");
                List<PersonalContactDTO> resultList = contactGroupService.getAllByGroupBySearch(requestDTO);
                ContactResponseDTO responseDTO = new ContactResponseDTO();
                if(resultList == null) return null;
                System.out.println(resultList.toString());
                responseDTO.setPersonalContactList(resultList);
                return responseDTO;
            } else return null;
        } else return null;
    }


}
