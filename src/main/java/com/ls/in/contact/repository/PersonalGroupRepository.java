package com.ls.in.contact.repository;

import com.ls.in.contact.domain.model.PersonalGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalGroupRepository extends JpaRepository<PersonalGroup, Integer> {
    @Query("SELECT pg FROM PersonalGroup pg WHERE pg.emp.empId = :empId")
    List<PersonalGroup> findAllByEmp(@Param("empId") Integer empId);
}
