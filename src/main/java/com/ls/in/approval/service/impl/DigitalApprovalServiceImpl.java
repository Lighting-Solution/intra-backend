package com.ls.in.approval.service.impl;


import com.ls.in.approval.domain.dao.DigitalApprovalDao;
import com.ls.in.approval.domain.model.DigitalApproval;
import com.ls.in.approval.dto.DigitalApprovalDTO;
import com.ls.in.approval.service.DigitalApprovalService;

import com.ls.in.approval.util.DigitalApprovalMapper;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.util.EmpMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DigitalApprovalServiceImpl implements DigitalApprovalService {

    private DigitalApprovalDao approvalDao;

    @Autowired
    public DigitalApprovalServiceImpl(DigitalApprovalDao approvalDao) {
        this.approvalDao = approvalDao;

    }

    @Override
    public DigitalApprovalDTO approvalRequest(Integer empId, String digitalApprovalName, EmpDTO empDTO) {
        Emp emp = EmpMapper.toEntity(empDTO);
            DigitalApproval digitalApproval = DigitalApproval.builder()
                    .drafterId(empDTO.getEmpId())
                    .digitalApprovalName(digitalApprovalName)
                    .digitalApprovalPath(empDTO.getEmpSign())
                    .digitalApprovalType(false)
                    .drafterStatus(true)
                    .managerStatus(false)
                    .ceoStatus(false)
                    .digitalApprovalCreateAt(LocalDateTime.now())

                    .digitalApprovalAt(null)


                    .emp(emp)
                    .build();
            DigitalApproval digitalApproval2 = approvalDao.save(digitalApproval);

            //---------------------------------------------------------
            return DigitalApprovalMapper.toDto(digitalApproval2);

    }

    @Override
    public List<DigitalApprovalDTO> getApprovalWaitingList(Integer empId) {
        List<DigitalApproval> digitalApprovalList = approvalDao.findByEmpEmpId(empId);
        return digitalApprovalList.stream()
                .map(DigitalApprovalMapper::toDto) // 엔티티를 DTO로 변환
                .collect(Collectors.toList());
    }


}
