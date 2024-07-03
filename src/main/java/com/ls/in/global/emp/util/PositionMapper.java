package com.ls.in.global.emp.util;

import com.ls.in.global.emp.domain.dto.PositionDTO;
import com.ls.in.global.emp.domain.model.Position;
import com.ls.in.global.util.Formats;

public class PositionMapper {
    public static PositionDTO toDTO(Position position) {
        if(position == null) return null;
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setPositionId(position.getPositionId());
        positionDTO.setPositionName(position.getPositionName());
        return positionDTO;
    }

    public static Position toEntity(PositionDTO positionDTO) {
        if(positionDTO == null) return null;
        Integer id = Formats.toInteger(positionDTO.getPositionId());
        return Position.builder()
                .positionId(id)
                .positionName(positionDTO.getPositionName())
                .build();
    }
}
