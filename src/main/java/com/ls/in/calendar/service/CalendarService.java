package com.ls.in.calendar.service;

import com.ls.in.calendar.domain.model.Calendar;
import com.ls.in.calendar.dto.CalendarDTO;

import java.util.List;

public interface CalendarService {
    List<CalendarDTO> getAllEvents();
    CalendarDTO getEventById(Integer id);
    CalendarDTO createOrUpdateEvent(CalendarDTO calendarDTO);
    void deleteEvent(Integer id);

}
