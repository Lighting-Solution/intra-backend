package com.ls.in.contact.repository;

import com.ls.in.contact.domain.model.ContactGroup;
import com.ls.in.contact.domain.model.PersonalContact;
import com.ls.in.contact.dto.ContactFilterPageDTO;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ContactGroupCustomRepository {
    Page<PersonalContact> search(ContactFilterPageDTO data);

    List<ContactGroup> findByPersonalContactAndPersonalGroup(List<Integer> contactIds, List<Integer> groupIds);
}
