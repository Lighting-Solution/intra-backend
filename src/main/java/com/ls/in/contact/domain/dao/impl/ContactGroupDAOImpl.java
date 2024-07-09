package com.ls.in.contact.domain.dao.impl;

import com.ls.in.contact.domain.dao.ContactGroupDAO;
import com.ls.in.contact.domain.model.ContactGroup;
import com.ls.in.contact.domain.model.PersonalContact;
import com.ls.in.contact.dto.ContactFilterDTO;
import com.ls.in.contact.repository.ContactGroupRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository("contactGroupDAO")
public class ContactGroupDAOImpl implements ContactGroupDAO {

    private final ContactGroupRepository contactGroupRepository;


    @Autowired
    public ContactGroupDAOImpl(ContactGroupRepository contactGroupRepository) {
        this.contactGroupRepository = contactGroupRepository;
    }

    @Override
    public List<PersonalContact> findAllByGroup(ContactFilterDTO data) throws DataAccessException {
        return contactGroupRepository.search(data);
    }

    @Override
    public boolean saveAll(List<ContactGroup> contactGroups) throws DataAccessException {
        try{
            contactGroupRepository.saveAll(contactGroups);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteByFKs(List<Integer> contactIds, List<Integer> groupIds) throws DataAccessException {
        try{
            contactGroupRepository.deleteByFKs(contactIds, groupIds);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<ContactGroup> findAllByEmp(Integer empId) throws DataAccessException {
        return contactGroupRepository.findAllByEmpId(empId);
    }

    @Override
    public Set<String> findExistingContactGroupCombinations(List<Integer> contactIds, List<Integer> groupIds) {
        List<ContactGroup> existingContactGroups = contactGroupRepository.findByPersonalContactAndPersonalGroup(contactIds, groupIds);
        return existingContactGroups.stream()
                .map(cg -> cg.getPersonalContact().getPersonalContactId() + "-" + cg.getPersonalGroup().getPersonalGroupId())
                .collect(Collectors.toSet());
    }


}
