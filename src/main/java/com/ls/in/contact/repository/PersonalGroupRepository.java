package com.ls.in.contact.repository;

import com.ls.in.contact.domain.model.PersonalGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalGroupRepository extends JpaRepository<PersonalGroup, Integer> {
}
