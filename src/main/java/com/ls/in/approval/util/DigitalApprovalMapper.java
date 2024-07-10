package com.ls.in.approval.util;

import com.ls.in.approval.domain.model.DigitalApproval;
import com.ls.in.approval.dto.DigitalApprovalDTO;
import com.ls.in.global.emp.util.EmpMapper;

public class DigitalApprovalMapper {
    public static DigitalApprovalDTO toDto(DigitalApproval digitalApproval) {
        if(digitalApproval == null) return null;
        return DigitalApprovalDTO.builder()
                .digitalApprovalId(digitalApproval.getDigitalApprovalId())
                .drafterId(digitalApproval.getDrafterId())
                .digitalApprovalName(digitalApproval.getDigitalApprovalName())
                .digitalApprovalPath(digitalApproval.getDigitalApprovalPath())
                .digitalApprovalType(digitalApproval.isDigitalApprovalType())
                .drafterStatus(digitalApproval.isDrafterStatus())
                .managerStatus(digitalApproval.isManagerStatus())
                .ceoStatus(digitalApproval.isCeoStatus())
                .managerRejectAt(digitalApproval.getManagerRejectAt())
                .ceoRejectAt(digitalApproval.getCeoRejectAt())
                .digitalApprovalCreateAt(digitalApproval.getDigitalApprovalCreateAt())
                .digitalApprovalAt(digitalApproval.getDigitalApprovalAt())

                .empDTO(EmpMapper.toDto(digitalApproval.getEmp()))
                .build();

    }
/*
    public static DigitalApproval toEntity(DigitalApprovalDTO digitalApprovalDTO) {
        if(digitalApprovalDTO == null) return null;
        Integer digitalApprovalId = digitalApprovalDTO.getDigitalApprovalId();
        Integer drafterId = digitalApprovalDTO.getDrafterId();

        return DigitalApproval.builder()
                .digitalApprovalId(digitalApprovalId)
                .drafterId(drafterId)
                .digitalApprovalName(digitalApprovalDTO.getDigitalApprovalName())
                .digitalApprovalPath(digitalApprovalDTO.getDigitalApprovalPath())
                .digitalApprovalType(digitalApprovalDTO.isDigitalApprovalType())
                .drafterStatus(digitalApprovalDTO.isDrafterStatus())
                .managerStatus(digitalApprovalDTO.isManagerStatus())
                .ceoStatus(digitalApprovalDTO.isCeoStatus())
                .managerRejectAt(digitalApprovalDTO.getManagerRejectAt())
                .ceoRejectAt(digitalApprovalDTO.getCeoRejectAt())
                .digitalApprovalCreateAt(digitalApprovalDTO.getDigitalApprovalCreateAt())
                .digitalApprovalAt(digitalApprovalDTO.getDigitalApprovalAt())

                .emp(EmpMapper.toEntity(digitalApprovalDTO.getEmpDTO()))
                .build();
    }

 */
}
