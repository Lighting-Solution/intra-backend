package com.ls.in.approval.service;

import com.ls.in.approval.dto.DigitalApprovalDTO;
import com.ls.in.global.emp.domain.dto.EmpDTO;

public interface DigitalApprovalService {
    public DigitalApprovalDTO approvalRequest(Integer empId, String digitalApprovalName, EmpDTO empDTO);

}
