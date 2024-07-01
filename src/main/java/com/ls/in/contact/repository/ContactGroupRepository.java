package com.ls.in.contact.repository;

import com.ls.in.contact.domain.model.ContactGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactGroupRepository extends JpaRepository<ContactGroup, Integer> {

    @Query("SELECT cg "
            + "FROM ContactGroup cg "
            + "JOIN FETCH cg.personalContact pc "
            + "JOIN FETCH cg.personalGroup pg "
            + "JOIN pg.emp e "
            + "WHERE e.empId = :id")
    List<ContactGroup> findAllByEmpId(@Param("id") Integer id);
}
