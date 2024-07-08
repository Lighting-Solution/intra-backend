package com.ls.in.calendar.dto;

import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.domain.model.Emp;
import lombok.Data;

@Data
public class ParticipantDTO {

    private Integer participantId;
    private EmpDTO empDTO;
    private CalendarDTO calendarDTO;
}