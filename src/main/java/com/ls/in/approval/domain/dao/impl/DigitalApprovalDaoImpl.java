package com.ls.in.approval.domain.dao.impl;

import com.ls.in.approval.domain.dao.DigitalApprovalDao;
import com.ls.in.approval.domain.model.DigitalApproval;
import com.ls.in.approval.repository.DigitalApprovalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
