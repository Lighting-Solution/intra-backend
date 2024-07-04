package com.ls.in.global.emp.service;

import com.ls.in.global.emp.domain.dto.PositionDTO;
import com.ls.in.global.emp.exception.PositionNotFoundException;

import java.util.List;

public interface PositionService {
    List<PositionDTO> getAllPosition() throws PositionNotFoundException;
    PositionDTO getPositionById(int positionId) throws PositionNotFoundException;
}
