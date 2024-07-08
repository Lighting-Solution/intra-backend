package com.ls.in.calendar.dto;

import com.ls.in.global.emp.domain.dto.EmpDTO;
import lombok.Data;

@Data
public class ParticipantDTO {

    private Integer participantId;
    private Integer empId;
    private Integer calendarId;

    private EmpDTO emp;
    private String department;
    private String name;
}