package com.ls.in.approval.service;

import com.ls.in.approval.domain.model.DigitalApproval;
import com.ls.in.approval.dto.DigitalApprovalDTO;
import com.ls.in.global.emp.domain.dto.EmpDTO;

import java.util.List;

public interface DigitalApprovalService {

    DigitalApprovalDTO approvalRequest(Integer empId, String digitalApprovalName, EmpDTO empDTO);
  
    List<DigitalApprovalDTO> getApprovalWaitingList(Integer empId);
}
