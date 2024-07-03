package com.ls.in.approval.domain.dao.impl;

import com.ls.in.approval.domain.dao.DigitalApprovalDao;
import com.ls.in.approval.domain.model.DigitalApproval;
import com.ls.in.approval.repository.DigitalApprovalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DigitalApprovalDaoImpl implements DigitalApprovalDao {
    private DigitalApprovalRepository digitalApprovalRepository;

    @Autowired
    public DigitalApprovalDaoImpl(DigitalApprovalRepository digitalApprovalRepository){
        this.digitalApprovalRepository = digitalApprovalRepository;
    }

    public List<DigitalApproval> findAll() throws DataAccessException {
        return digitalApprovalRepository.findAll();
    }
}
