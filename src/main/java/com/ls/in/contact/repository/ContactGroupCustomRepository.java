package com.ls.in.contact.repository;

import com.ls.in.contact.domain.model.ContactGroup;
import com.ls.in.contact.domain.model.PersonalContact;
import com.ls.in.contact.dto.ContactFilterDTO;

import java.util.List;


public interface ContactGroupCustomRepository {
    List<PersonalContact> search(ContactFilterDTO data);

    List<ContactGroup> findByPersonalContactAndPersonalGroup(List<Integer> contactIds, List<Integer> groupIds);
}
