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

    private EmpService empService;

    @Autowired
    public DigitalApprovalServiceImpl(DigitalApprovalDao approvalDao, EmpService empService) {
        this.approvalDao = approvalDao;
        this.empService = empService;

    }

    @Override
    public DigitalApprovalDTO approvalRequest(Integer empId, String digitalApprovalName) {
        EmpDTO empDTO = empService.getEmpById(empId);

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
                .emp(emp)
                .build();

        DigitalApproval digitalApproval2 = approvalDao.save(digitalApproval);

        //---------------------------------------------------------

        EmpDTO change = EmpMapper.toDto(digitalApproval2.getEmp());
        /**
         *  여기 리턴하는 EMP 값이 부장의 값인지 기안자의 값인지?
         *  사원의 값이면 바꿀 필요가 없을 거라고 생각하는뎅...
         */
        return DigitalApprovalDTO.builder()
                .digitalApprovalId(digitalApproval2.getDigitalApprovalId())
                .drafterId(digitalApproval2.getEmp().getEmpId())
                .digitalApprovalName(digitalApprovalName)
                .digitalApprovalPath(digitalApproval2.getEmp().getEmpSign())
                .digitalApprovalType(false)
                .drafterStatus(false)
                .managerStatus(false)
                .ceoStatus(false)
                .empDTO(change)
                .build();
    }

}
