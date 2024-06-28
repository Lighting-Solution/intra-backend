package com.ls.in.contact.repository;

import com.ls.in.contact.domain.model.ContactGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactGroupRepository extends JpaRepository<ContactGroup, Integer> {
}
