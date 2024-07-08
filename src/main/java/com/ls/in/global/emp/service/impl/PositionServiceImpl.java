package com.ls.in.global.emp.service.impl;


import com.ls.in.global.emp.domain.dao.PositionDAO;
import com.ls.in.global.emp.domain.dto.PositionDTO;
import com.ls.in.global.emp.domain.model.Position;
import com.ls.in.global.emp.exception.PositionNotFoundException;
import com.ls.in.global.emp.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("positionService")
public class PositionServiceImpl implements PositionService {

    private final PositionDAO positionDAO;

    @Autowired
    public PositionServiceImpl(PositionDAO positionDAO) {
        this.positionDAO = positionDAO;
    }

    @Override
    public List<PositionDTO> getAllPosition() throws PositionNotFoundException {
        List<Position> resultPosition = positionDAO.findALl();
        List<PositionDTO> returnDTOList = new ArrayList<>();
        for (Position position : resultPosition) {
            PositionDTO positionDTO = new PositionDTO(position.getPositionId(), position.getPositionName());
            returnDTOList.add(positionDTO);
        }
        return returnDTOList;
    }

    @Override
    public PositionDTO getPositionById(int positionId) throws PositionNotFoundException {
        Position resultPosition = positionDAO.findById(positionId);
        return new PositionDTO(resultPosition.getPositionId(), resultPosition.getPositionName());
    }
}
