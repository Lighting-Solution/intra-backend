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
import java.util.Optional;
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
                    .digitalApprovalType(false)
                    .drafterStatus(true)
                    .managerStatus(false)
                    .ceoStatus(false)
                    .digitalApprovalCreateAt(LocalDateTime.now())
                    .digitalApprovalAt(null)
                    .emp(emp)
                    .build();
        DigitalApproval digitalApproval2 = approvalDao.save(digitalApproval);
        return DigitalApprovalMapper.toDto(digitalApproval2);
    }

    @Override
    public List<DigitalApprovalDTO> getApprovalWaitingList() {
        List<DigitalApproval> digitalApprovalList = approvalDao.findAll();
        return digitalApprovalList.stream()
                .map(DigitalApprovalMapper::toDto) // 엔티티를 DTO로 변환
                .collect(Collectors.toList());
    }

    @Override
    public List<DigitalApprovalDTO> getApprovalWaitingListByManager(Integer department) {
        List<DigitalApproval> digitalApprovalList = approvalDao.findAll();

        List<DigitalApprovalDTO> digitalApprovalDTOList = new ArrayList<>();
        for(DigitalApproval item : digitalApprovalList ){
            DigitalApprovalDTO digitalApprovalDTO = DigitalApprovalMapper.toDto(item);

            // 기안자의 id의 부서와 position 의 부서가 같을 경우 list 추가
           Integer drafterPosition = digitalApprovalDTO.getEmpDTO().getDepartment().getDepartmentId();
           if(drafterPosition.equals(department)){
               digitalApprovalDTOList.add(digitalApprovalDTO);
           }

        }
        return digitalApprovalDTOList;
    }

    @Override
    public List<DigitalApprovalDTO> getApprovalWaitingListByEmployee(Integer empId) {
        List<DigitalApproval> digitalApprovalList = approvalDao.findAll();

        List<DigitalApprovalDTO> digitalApprovalDTOList = new ArrayList<>();
        for(DigitalApproval item : digitalApprovalList ){
            DigitalApprovalDTO digitalApprovalDTO = DigitalApprovalMapper.toDto(item);

            // 기안자의 id와 emp id가 같을 경우 추가
            Integer drafterId = digitalApprovalDTO.getDrafterId();
            if(drafterId.equals(empId)){
                digitalApprovalDTOList.add(digitalApprovalDTO);
                System.out.println(digitalApprovalDTO);
            }

        }
        return digitalApprovalDTOList;
    }


    @Override
    public DigitalApprovalDTO getDrafterId(Integer digitalApprovalId) {
        Optional<DigitalApproval> optionalDigitalApproval = approvalDao.findById(digitalApprovalId);
        DigitalApproval digitalApproval = optionalDigitalApproval.get();
        return DigitalApprovalMapper.toDto(digitalApproval);
    }

    @Override
    public void updatePath(Integer digitalApprovalId,String outputPdfPath) {
        approvalDao.updatePath(digitalApprovalId, outputPdfPath);
    }

    @Override
    public void updateStatus(Integer digitalApprovalId, String type) {
        approvalDao.updateStatus(digitalApprovalId, type);
    }

    @Override
    public void updateRejectionStatus(Integer digitalApprovalId) {
        approvalDao.updateRejectionStatus(digitalApprovalId);
    }

}
