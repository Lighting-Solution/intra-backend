package com.ls.in.approval.repository;

import com.ls.in.approval.domain.model.DigitalApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DigitalApprovalRepository extends JpaRepository<DigitalApproval, Integer> {
}
