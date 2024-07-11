package com.ls.in.global.emp.repository;

import com.ls.in.global.emp.domain.model.Emp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface EmpRepository extends JpaRepository<Emp, Integer>, EmpCustomRepository {

    @Query("SELECT e FROM Emp e WHERE e.department.departmentId = :departmentId")
    List<Emp> findAllByDepartment(@Param("departmentId") Integer departmentId);

    @Query("SELECT e FROM Emp e WHERE e.position.positionId = :positionId")
    Emp findByPosition(@Param("positionId") Integer positionId);

    @Query("SELECT e FROM Emp e " +
            "WHERE e.department.departmentId IN (SELECT e2.department.departmentId FROM Emp e2 WHERE e2.empId = :empId) " +
            "AND e.position.positionId = :positionId")
    Emp findByEmpIdAndDepartmentAndPosition(@Param("empId") Integer empId, @Param("positionId") Integer positionId);

    Optional<Emp> findByAccountId(String accountId);

    @Query("SELECT e FROM Emp e WHERE e.accountId = :accountId AND e.accountPw = :accountPw")
    Optional<Emp> findByAccountIdAndAccountPw(@Param("accountId") String accountId, @Param("accountPw") String accountPw);

    @Query("Select e FROM Emp e WHERE e.position.positionId =:positionId and e.department.departmentId =:departmentId")
    Emp findByPositionIdAndDepartmentId(int positionId, int departmentId);
}
