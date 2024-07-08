package com.ls.in.calendar.util;

import com.ls.in.calendar.domain.model.Participant;
import com.ls.in.calendar.dto.ParticipantDTO;
import com.ls.in.global.emp.util.EmpMapper;
import org.springframework.stereotype.Component;

public class ParticipantMapper {

    public static ParticipantDTO toDto(Participant participant) {
        ParticipantDTO dto = new ParticipantDTO();
        dto.setParticipantId(participant.getParticipantId());
        dto.setEmp(EmpMapper.toDto(participant.getEmp()));
        dto.setCalendarId(participant.getCalendar().getCalendarId());

        return dto;
    }
}
