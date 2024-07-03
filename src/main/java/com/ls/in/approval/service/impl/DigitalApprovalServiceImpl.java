package com.ls.in.approval.service.impl;


import com.ls.in.approval.domain.dao.DigitalApprovalDao;
import com.ls.in.approval.domain.model.DigitalApproval;
import com.ls.in.approval.dto.DigitalApprovalDTO;
import com.ls.in.approval.service.DigitalApprovalService;

import com.ls.in.approval.util.DigitalApprovalMapper;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.service.EmpService;
import com.ls.in.global.emp.util.EmpMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
                .drafterStatus(false)
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

}
