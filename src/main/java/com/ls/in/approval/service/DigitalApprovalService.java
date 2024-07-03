package com.ls.in.approval.service;

import com.ls.in.approval.dto.DigitalApprovalDTO;

public interface DigitalApprovalService {
    public DigitalApprovalDTO approvalRequest(Integer empId, String digitalApprovalName);

}
