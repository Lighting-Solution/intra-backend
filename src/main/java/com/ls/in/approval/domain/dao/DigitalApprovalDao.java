package com.ls.in.approval.domain.dao;

import com.ls.in.approval.domain.model.DigitalApproval;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface DigitalApprovalDao {
    List<DigitalApproval> findAll() throws DataAccessException;
}
