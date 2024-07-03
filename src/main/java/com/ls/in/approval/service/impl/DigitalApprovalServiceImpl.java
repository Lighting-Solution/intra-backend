package com.ls.in.approval.service.impl;


import com.ls.in.approval.domain.dao.DigitalApprovalDao;
import com.ls.in.approval.domain.model.DigitalApproval;
import com.ls.in.approval.dto.DigitalApprovalDTO;
import com.ls.in.approval.service.DigitalApprovalService;
import com.ls.in.approval.util.DigitalApprovalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DigitalApprovalServiceImpl implements DigitalApprovalService {

    private DigitalApprovalDao approvalDao;

    @Autowired
    public DigitalApprovalServiceImpl(DigitalApprovalDao approvalDao) {
        this.approvalDao = approvalDao;
    }

    public List<DigitalApprovalDTO> getAllApprovals() {
        List<DigitalApprovalDTO> digitDTOList = new ArrayList<>();
        List<DigitalApproval> resultDigitList = approvalDao.findAll();

        for(DigitalApproval digitApproval : resultDigitList) {
            DigitalApprovalDTO tempDTO = DigitalApprovalMapper.toDto(digitApproval);
            digitDTOList.add(tempDTO);

        }
        return digitDTOList;
    }

}
