package com.ls.in.global.emp.util;

import com.ls.in.global.emp.domain.dto.PositionDTO;
import com.ls.in.global.emp.domain.model.Position;

public class PositionMapper {
    public static PositionDTO toDto(Position position) {
        if(position == null) return null;
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setPositionId(position.getPositionId());
        positionDTO.setPositionName(position.getPositionName());
        return positionDTO;
    }

    public static Position toEntity(PositionDTO positionDTO) {
        if(positionDTO == null) return null;
        return Position.builder()
                .positionId(positionDTO.getPositionId())
                .positionName(positionDTO.getPositionName())
                .build();
    }
}
