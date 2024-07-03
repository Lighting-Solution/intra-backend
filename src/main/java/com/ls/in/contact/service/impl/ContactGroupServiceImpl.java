package com.ls.in.contact.service.impl;

import com.ls.in.contact.domain.dao.ContactGroupDAO;
import com.ls.in.contact.domain.model.ContactGroup;
import com.ls.in.contact.domain.model.PersonalContact;
import com.ls.in.contact.dto.ContactGroupDTO;
import com.ls.in.contact.dto.PersonalContactDTO;
import com.ls.in.contact.exception.ContactGroupNotFoundException;
import com.ls.in.contact.exception.PersonalContactNotFoundException;
import com.ls.in.contact.service.ContactGroupService;
import com.ls.in.contact.util.mapper.ContactGroupMapper;
import com.ls.in.contact.util.mapper.PersonalContactMapper;
import com.ls.in.global.util.Formats;
import com.ls.in.global.util.PageNation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

/*    @Override
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
    }*/

    @Override
    public List<ContactGroupDTO> getAllPersonalContactByGroup(int empId, int groupId) throws PersonalContactNotFoundException {
        Page<ContactGroup> result = contactGroupDAO.findAllPersonalContactByGroup(PageNation.setPage(0,10), empId,groupId);
        List<ContactGroupDTO> responseList = new ArrayList<>();
        for(ContactGroup contactGroup : result) {
            ContactGroupDTO tempDTO = ContactGroupMapper.toDTO(contactGroup);
            responseList.add(tempDTO);
        }
        return responseList;
    }
}
