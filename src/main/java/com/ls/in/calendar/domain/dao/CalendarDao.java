package com.ls.in.calendar.domain.dao;

import com.ls.in.calendar.domain.model.Calendar;

import java.util.List;

public interface CalendarDao {
    List<Calendar> getAllEvents();
    Calendar getEventById(Integer id);
    Calendar saveEvent(Calendar calendar);
    void deleteEvent(Integer id);

}
