package com.ls.in.approval.repository;

import com.ls.in.approval.domain.model.DigitalApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DigitalApprovalRepository extends JpaRepository<DigitalApproval, Integer> {
    // 결재 대기 문서 목록 조회
    List<DigitalApproval> findByEmpEmpId(Integer empId);
}
