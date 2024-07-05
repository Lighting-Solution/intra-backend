package com.ls.in.contact.service.impl;

import com.ls.in.contact.domain.dao.ContactGroupDAO;
import com.ls.in.contact.domain.model.ContactGroup;
import com.ls.in.contact.domain.model.PersonalContact;
import com.ls.in.contact.domain.model.PersonalGroup;
import com.ls.in.contact.dto.ContactFilterDTO;
import com.ls.in.contact.dto.ContactGroupDTO;
import com.ls.in.contact.dto.PersonalContactDTO;
import com.ls.in.contact.service.ContactGroupService;
import com.ls.in.contact.util.mapper.ContactGroupMapper;
import com.ls.in.contact.util.mapper.PersonalContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("contactGroupService")
public class ContactGroupServiceImpl implements ContactGroupService {

    private final ContactGroupDAO contactGroupDAO;

    @Autowired
    public ContactGroupServiceImpl(ContactGroupDAO contactGroupDAO) {
        this.contactGroupDAO = contactGroupDAO;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean createContactGroup(Map<String, Object> requestData) throws Exception {
        List<Integer> groupList = null;
        List<Integer> contactList = null;
        if(requestData.get("contact") instanceof List) {
            contactList = (List<Integer>) requestData.get("contact");
        } else return false;

        if(requestData.get("group") instanceof List) {
            groupList = (List<Integer>) requestData.get("group");
        } else return false;

        Set<String> existingCombinations = contactGroupDAO.findExistingContactGroupCombinations(contactList, groupList);
        List<ContactGroup> contactGroups = new ArrayList<>();
        for (int contactId : contactList) {
            for (int groupId : groupList) {
                String combinationKey = contactId + "-" + groupId;
                if(!existingCombinations.contains(combinationKey)) {
                    ContactGroup contactGroup = ContactGroup.builder()
                            .personalContact(PersonalContact.builder().personalContactId(contactId).build())
                            .personalGroup(PersonalGroup.builder().personalGroupId(groupId).build())
                            .build();
                    contactGroups.add(contactGroup);
                }
            }
        }
        return contactGroupDAO.saveAll(contactGroups);
    }

    @Override
    public boolean deleteContactGroup(Map<String, Object> requestData) throws Exception {
        List<Integer> contactList = (List<Integer>) requestData.get("contact");
        List<Integer> groupList = (List<Integer>) requestData.get("group");
        return contactGroupDAO.deleteByFKs(contactList, groupList);
    }

    @Override
    public List<ContactGroupDTO> getAllByEmpId(Integer empId) {
        List<ContactGroup> result = contactGroupDAO.findAllByEmp(empId);
        List<ContactGroupDTO> contactGroupDTOList  = new ArrayList<>();
        for(ContactGroup contactGroup : result) {
            ContactGroupDTO contactGroupDTO = ContactGroupMapper.toDto(contactGroup);
            contactGroupDTOList.add(contactGroupDTO);
        }
        return contactGroupDTOList;
    }

    @Override
    public List<PersonalContactDTO> getAllByGroupBySearch(ContactFilterDTO requestDTO) {
        List<PersonalContact> result = contactGroupDAO.findAllByGroup(requestDTO);
        List<PersonalContactDTO> responseList = new ArrayList<>();
        for(PersonalContact personalContact : result) {
            PersonalContactDTO tempDTO = PersonalContactMapper.toDto(personalContact);
            responseList.add(tempDTO);
        }
        return responseList;
    }
}
