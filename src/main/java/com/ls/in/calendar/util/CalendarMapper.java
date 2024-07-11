package com.ls.in.calendar.util;

import com.ls.in.calendar.domain.model.Calendar;
import com.ls.in.calendar.dto.CalendarDTO;

import java.util.stream.Collectors;

public class CalendarMapper {
    public static CalendarDTO toDto(Calendar calendar) {
        CalendarDTO calendarDTO = new CalendarDTO();
        calendarDTO.setCalendarId(calendar.getCalendarId());
        calendarDTO.setCalendarTitle(calendar.getCalendarTitle());
        calendarDTO.setCalendarCreateAt(calendar.getCalendarCreateAt());
        calendarDTO.setCalendarContent(calendar.getCalendarContent());
        calendarDTO.setCalendarStartAt(calendar.getCalendarStartAt());
        calendarDTO.setCalendarEndAt(calendar.getCalendarEndAt());
        if (calendar.getAttendees() != null) {
            calendarDTO.setAttendees(
                    calendar.getAttendees().stream()
                            .map(ParticipantMapper::toDto)
                            .collect(Collectors.toList())
            );
        }

        return calendarDTO;
    }

    public static Calendar toEntity(CalendarDTO calendarDTO) {
        if (calendarDTO == null) {
            throw new IllegalArgumentException("CalendarDTO cannot be null");
        }
        Calendar calendar = new Calendar();
        calendar.setCalendarId(calendarDTO.getCalendarId());
        calendar.setCalendarTitle(calendarDTO.getCalendarTitle());
        calendar.setCalendarCreateAt(calendarDTO.getCalendarCreateAt());
        calendar.setCalendarContent(calendarDTO.getCalendarContent());
        calendar.setCalendarStartAt(calendarDTO.getCalendarStartAt());
        calendar.setCalendarEndAt(calendarDTO.getCalendarEndAt());

        // Attendees 처리
        if (calendarDTO.getAttendees() != null) {
            calendar.setAttendees(
                    calendarDTO.getAttendees().stream()
                            .map(ParticipantMapper::toEntity)
                            .collect(Collectors.toList())
            );
        }

        return calendar;
    }
}
