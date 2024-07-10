package com.ls.in.calendar.dto;

import com.ls.in.global.emp.domain.dto.EmpDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDTO {

    private Integer participantId;
    private EmpDTO empDTO;
    private CalendarDTO calendarDTO;

}