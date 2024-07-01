package com.ls.in.contact.service.impl;

import com.ls.in.contact.domain.dao.ContactGroupDAO;
import com.ls.in.contact.domain.model.ContactGroup;
import com.ls.in.contact.dto.ContactGroupMapper;
import com.ls.in.contact.dto.ContactGroupDTO;
import com.ls.in.contact.service.ContactGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("contactGroupService")
public class ContactGroupServiceImpl implements ContactGroupService {

    private final ContactGroupDAO contactGroupDAO;

    @Autowired
    public ContactGroupServiceImpl(ContactGroupDAO contactGroupDAO) {
        this.contactGroupDAO = contactGroupDAO;
    }

    @Override
    public List<ContactGroupDTO> getAllContact(int empId) {
        Integer id = Integer.valueOf(empId);
        List<ContactGroup> result = contactGroupDAO.findAllByEmpId(id);
        List<ContactGroupDTO> contactGroupList = new ArrayList<>();
        for (ContactGroup contactGroup : result) {
            ContactGroupDTO contactGroupDTO = ContactGroupMapper.toDTO(contactGroup);
            contactGroupList.add(contactGroupDTO);
        }

        for (ContactGroupDTO dto : contactGroupList) {
            System.out.println(dto.toString());
        }
        return contactGroupList;
    }
}
