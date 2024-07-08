package com.ls.in.global.emp.repository;

import com.ls.in.global.emp.domain.model.Emp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpRepository extends JpaRepository<Emp, Integer> {
    Emp findByAccountId(String accountId);;
}
