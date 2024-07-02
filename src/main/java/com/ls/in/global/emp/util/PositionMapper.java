package com.ls.in.global.emp.util;

import com.ls.in.global.emp.domain.dto.PositionDTO;
import com.ls.in.global.emp.domain.model.Position;

public class PositionMapper {
    public static PositionDTO toDTO(Position position) {
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setPositionId(position.getPositionId());
        positionDTO.setPositionName(position.getPositionName());
        return positionDTO;
    }
}
