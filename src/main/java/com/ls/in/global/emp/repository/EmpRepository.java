package com.ls.in.global.emp.repository;

import com.ls.in.global.emp.domain.model.Emp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpRepository extends JpaRepository<Emp, Integer>, EmpCustomRepository {

    @Query("SELECT e FROM Emp e WHERE e.department.departmentId = :departmentId")
    Page<Emp> findAllByDepartment( Pageable pageable, @Param("departmentId") Integer departmentId);

    @Query("SELECT e FROM Emp e WHERE e.position.positionId = :positionId")
    Emp findByPosition(@Param("positionId") Integer positionId);

    @Query("SELECT e FROM Emp e " +
            "WHERE e.department.departmentId IN (SELECT e2.department.departmentId FROM Emp e2 WHERE e2.empId = :empId) " +
            "AND e.position.positionId = :positionId")
    Emp findByEmpIdAndDepartmentAndPosition(@Param("empId") Integer empId, @Param("positionId") Integer positionId);
}
