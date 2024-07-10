package com.ls.in.approval.service;

import com.ls.in.approval.dto.DigitalApprovalDTO;
import com.ls.in.global.emp.domain.dto.EmpDTO;

import java.util.List;

public interface DigitalApprovalService {

    DigitalApprovalDTO approvalRequest(Integer empId, String digitalApprovalName, EmpDTO empDTO);
    
    List<DigitalApprovalDTO> getApprovalWaitingList();

    DigitalApprovalDTO getDrafterId(Integer digitalApprovalId);

    void updatePath(Integer digitalApprovalId, String outputPdfPath);

    void updateStatus(Integer digitalApprovalId, String type);

    List<DigitalApprovalDTO> getApprovalWaitingListByManager(Integer department);

    List<DigitalApprovalDTO> getApprovalWaitingListByEmployee(Integer empId);

    void updateRejectionStatus(Integer digitalApprovalId, boolean managerStatus, boolean ceoStatus);
}
