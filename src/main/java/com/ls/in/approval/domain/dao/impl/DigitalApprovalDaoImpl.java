package com.ls.in.approval.domain.dao.impl;

import com.ls.in.approval.domain.dao.DigitalApprovalDao;
import com.ls.in.approval.domain.model.DigitalApproval;
import com.ls.in.approval.repository.DigitalApprovalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
public class DigitalApprovalDaoImpl implements DigitalApprovalDao {
    private DigitalApprovalRepository digitalApprovalRepository;

    @Autowired
    public DigitalApprovalDaoImpl(DigitalApprovalRepository digitalApprovalRepository){
        this.digitalApprovalRepository = digitalApprovalRepository;
    }

    @Override
    public DigitalApproval save(DigitalApproval digitalApproval) {
        DigitalApproval responseDigitalApproval = digitalApprovalRepository.save(digitalApproval);
        return responseDigitalApproval;

    }

    @Override
    public List<DigitalApproval> findAll() {
        return digitalApprovalRepository.findAll();
    }

    @Override
    public Optional<DigitalApproval> findById(Integer digitalApprovalId) {
        return digitalApprovalRepository.findById(digitalApprovalId);
    }

    @Override
    public void updatePath(Integer digitalApprovalId, String outputPdfPath) {
        DigitalApproval existingApproval  = digitalApprovalRepository.findById(digitalApprovalId).orElseThrow();
        existingApproval.setDigitalApprovalPath(outputPdfPath);
        DigitalApproval updatedDigitalApproval = digitalApprovalRepository.save(existingApproval);
    }

    @Override
    public void updateStatus(Integer digitalApprovalId, String type) {
        DigitalApproval existingApproval  = digitalApprovalRepository.findById(digitalApprovalId).orElseThrow();
        // 매니저 상태 변경
        if(type.equals("manager")){
            existingApproval.setManagerStatus(true);
            System.out.println(existingApproval);
        } else if(type.equals("ceo")){
            existingApproval.setCeoStatus(true);
            existingApproval.setDigitalApprovalAt(LocalDateTime.now());

        } else {
            System.out.println("등록된 type 이아닙니다.");
        }
        DigitalApproval updatedDigitalApproval = digitalApprovalRepository.save(existingApproval);
    }

    @Override
    public void updateRejectionStatus(Integer digitalApprovalId, boolean managerStatus, boolean ceoStatus) {
        DigitalApproval existingApproval = digitalApprovalRepository.findById(digitalApprovalId)
                .orElseThrow(() -> new EntityNotFoundException("DigitalApproval not found"));
        // 반려 상태로 설정
        existingApproval.setDigitalApprovalType(true);

        // 부장 결재 반려 시간
        if(!managerStatus){
            existingApproval.setManagerRejectAt(LocalDateTime.now());
        } else { // ceo 반려 시간
            if(!ceoStatus){
                existingApproval.setCeoRejectAt(LocalDateTime.now());
            }
        }


        DigitalApproval updatedRejectionDigitalApproval = digitalApprovalRepository.save(existingApproval);
    }


}
