package com.ls.in.calendar.util;

import com.ls.in.calendar.domain.model.Participant;
import com.ls.in.calendar.dto.ParticipantDTO;
import com.ls.in.global.emp.util.EmpMapper;
import org.springframework.stereotype.Component;

public class ParticipantMapper {

    public static ParticipantDTO toDto(Participant participant) {
        if (participant == null) {
            return null;
        }

        ParticipantDTO dto = new ParticipantDTO();
        dto.setParticipantId(participant.getParticipantId());
        dto.setEmpDTO(EmpMapper.toDto(participant.getEmp()));
        dto.setCalendarDTO(CalendarMapper.toDto(participant.getCalendar()));

        return dto;
    }

    public static Participant toEntity(ParticipantDTO participantDTO) {
        if (participantDTO == null) {
            return null;
        }

        Participant participant = new Participant();
        participant.setParticipantId(participantDTO.getParticipantId());
        participant.setEmp(EmpMapper.toEntity(participantDTO.getEmpDTO()));
        participant.setCalendar(CalendarMapper.toEntity(participantDTO.getCalendarDTO()));

        return participant;
    }
}
