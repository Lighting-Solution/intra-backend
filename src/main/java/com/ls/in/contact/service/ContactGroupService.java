package com.ls.in.contact.service;

import com.ls.in.contact.dto.ContactFilterDTO;
import com.ls.in.contact.dto.ContactGroupDTO;
import com.ls.in.contact.dto.PersonalContactDTO;

import java.util.List;
import java.util.Map;

public interface ContactGroupService {
    List<ContactGroupDTO> getAllByEmpId(Integer empId);
    List<PersonalContactDTO> getAllByGroupBySearch(ContactFilterDTO requestDTO);
    boolean createContactGroup(Map<String, Object> requestData) throws  Exception;
    boolean deleteContactGroup(Map<String, Object> requestData) throws Exception;
}
