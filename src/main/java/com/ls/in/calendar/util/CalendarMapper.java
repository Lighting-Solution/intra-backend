package com.ls.in.calendar.util;

import com.ls.in.calendar.domain.model.Calendar;
import com.ls.in.calendar.dto.CalendarDTO;

public class CalendarMapper {
    public static CalendarDTO toDto(Calendar calendar) {
        CalendarDTO calendarDTO = new CalendarDTO();
        calendarDTO.setCalendarId(calendar.getCalendarId());
        calendarDTO.setCalendarTitle(calendar.getCalendarTitle());
        calendarDTO.setCalendarCreateAt(calendar.getCalendarCreateAt());
        calendarDTO.setCalendarContent(calendar.getCalendarContent());
        calendarDTO.setCalendarStartAt(calendar.getCalendarStartAt());
        calendarDTO.setCalendarEndAt(calendar.getCalendarEndAt());
        return calendarDTO;
    }

    public static Calendar toEntity(CalendarDTO calendarDTO) {
        Calendar calendar = new Calendar();
        calendar.setCalendarId(calendarDTO.getCalendarId());
        calendar.setCalendarTitle(calendarDTO.getCalendarTitle());
        calendar.setCalendarCreateAt(calendarDTO.getCalendarCreateAt());
        calendar.setCalendarContent(calendarDTO.getCalendarContent());
        calendar.setCalendarStartAt(calendarDTO.getCalendarStartAt());
        calendar.setCalendarEndAt(calendarDTO.getCalendarEndAt());
        return calendar;
    }
}
