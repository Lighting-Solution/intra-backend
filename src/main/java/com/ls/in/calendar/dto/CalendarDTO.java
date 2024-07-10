package com.ls.in.calendar.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarDTO {
    private Integer calendarId;

    private String calendarTitle;

    private LocalDateTime calendarCreateAt;

    private String calendarContent;

    private LocalDateTime calendarStartAt;

    private LocalDateTime calendarEndAt;

    private List<ParticipantDTO> attendees;
}
