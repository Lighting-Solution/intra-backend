package com.ls.in.approval.domain.dao;

import com.ls.in.approval.domain.model.DigitalApproval;
import com.ls.in.approval.dto.DigitalApprovalDTO;
import com.ls.in.global.emp.domain.model.Emp;

import java.util.List;

public interface DigitalApprovalDao {

    DigitalApproval save(DigitalApproval digitalApproval);

    List<DigitalApproval> findByEmpEmpId(Integer empId);
}
