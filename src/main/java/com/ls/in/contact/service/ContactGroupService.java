package com.ls.in.contact.service;

import com.ls.in.contact.dto.ContactFilterPageDTO;
import com.ls.in.contact.dto.ContactGroupDTO;
import com.ls.in.contact.dto.PersonalContactDTO;
import com.ls.in.contact.exception.ContactGroupNotFoundException;
import com.ls.in.contact.exception.PersonalContactNotFoundException;

import java.util.List;
import java.util.Map;

public interface ContactGroupService {
    List<ContactGroupDTO> getAllByEmpId(Integer empId);
    List<PersonalContactDTO> getAllByGroupBySearch(ContactFilterPageDTO requestDTO);
    boolean createContactGroup(Map<String, Object> requestData) throws  Exception;
    boolean deleteContactGroup(Map<String, Object> requestData) throws Exception;
}
