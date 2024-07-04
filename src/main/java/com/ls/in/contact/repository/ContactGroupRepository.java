package com.ls.in.contact.repository;

import com.ls.in.contact.domain.model.ContactGroup;
import com.ls.in.contact.domain.model.PersonalContact;
import com.ls.in.global.emp.domain.model.Emp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactGroupRepository extends JpaRepository<ContactGroup, Integer>, ContactGroupCustomRepository {

    @Query("SELECT cg "
            + "FROM ContactGroup cg "
            + "JOIN FETCH cg.personalContact pc "
            + "JOIN FETCH cg.personalGroup pg "
            + "JOIN pg.emp e "
            + "WHERE e.empId = :empId")
    List<ContactGroup> findAllByEmpId(@Param("empId") Integer empId);

/*    @Query("SELECT pc "
            + "FROM ContactGroup cg "
            + "JOIN cg.personalContact pc "
            + "JOIN cg.personalGroup pg "
            + "JOIN pg.emp e "
            + "WHERE pg.personalGroupId = :groupId ")
    Page<PersonalContact> findAllByEmpIdByGroupId(Pageable pageable, @Param("groupId") Integer groupId);*/

    @Modifying
    @Query("DELETE FROM ContactGroup cg " +
            "WHERE cg.personalContact.personalContactId IN :contactIds " +
            "AND cg.personalGroup.personalGroupId IN :groupIds")
    void deleteByFKs(@Param("contactIds") List<Integer> contactIds, @Param("groupIds") List<Integer> groupIds);

}
