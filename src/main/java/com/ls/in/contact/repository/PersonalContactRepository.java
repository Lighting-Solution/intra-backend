package com.ls.in.contact.repository;

import com.ls.in.contact.domain.model.PersonalContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalContactRepository extends JpaRepository<PersonalContact, Integer> {

    @Query("SELECT pc FROM PersonalContact pc WHERE pc.emp.empId = :empId")
    List<PersonalContact> findAllByEmp(@Param("empId") Integer id);
}
