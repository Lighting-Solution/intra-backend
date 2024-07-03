package com.ls.in.approval.util;

import com.ls.in.approval.domain.model.DigitalApproval;
import com.ls.in.approval.dto.DigitalApprovalDTO;

public class DigitalApprovalMapper {
    public static DigitalApprovalDTO toDto(DigitalApproval digitalApproval) {
        if(digitalApproval == null) return null;

        DigitalApprovalDTO digitDTO = new DigitalApprovalDTO();
        digitDTO.setDigital_approval_id(digitalApproval.getDigitalApprovalId());
        digitDTO.setCeo_status(digitalApproval.isCeoStatus());
        digitDTO.setName(digitalApproval.getDigitalApprovalName());
        digitDTO.setPath(digitalApproval.getDigitalApprovalPath());
        digitDTO.setDraft_id(digitalApproval.getDrafterId());
        digitDTO.setDrafter_status(digitalApproval.isDrafterStatus());
        digitDTO.setDrafter_status(digitalApproval.isManagerStatus());
        digitDTO.setEmp_id(digitalApproval.getEmp().getEmpId());
        return digitDTO;

    }


}
