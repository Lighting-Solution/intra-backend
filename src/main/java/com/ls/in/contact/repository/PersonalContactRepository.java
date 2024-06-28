package com.ls.in.contact.repository;

import com.ls.in.contact.domain.model.PersonalContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalContactRepository extends JpaRepository<PersonalContact, Integer> {
}
