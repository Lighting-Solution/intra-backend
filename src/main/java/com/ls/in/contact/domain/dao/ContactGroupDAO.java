package com.ls.in.contact.domain.dao;

import com.ls.in.contact.domain.model.ContactGroup;
import com.ls.in.contact.domain.model.PersonalContact;
import com.ls.in.contact.dto.ContactFilterDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Set;

public interface ContactGroupDAO {
    List<PersonalContact> findAllByGroup(ContactFilterDTO data) throws DataAccessException;
    boolean saveAll(List<ContactGroup> contactGroups) throws DataAccessException;
    boolean deleteByFKs(List<Integer> contactIds, List<Integer> groupIds) throws DataAccessException;
    List<ContactGroup> findAllByEmp(Integer empId) throws DataAccessException;

    Set<String> findExistingContactGroupCombinations(List<Integer> contactIds, List<Integer> groupIds);
}
