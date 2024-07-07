package com.ls.in.calendar.service.impl;

import com.ls.in.calendar.domain.dao.CalendarDao;
import com.ls.in.calendar.domain.model.Calendar;
import com.ls.in.calendar.dto.CalendarDTO;
import com.ls.in.calendar.service.CalendarService;
import com.ls.in.calendar.util.CalendarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalendarServiceImpl implements CalendarService {
    @Autowired
    private CalendarDao calendarDao;

    public List<CalendarDTO> getAllEvents() {
        List<Calendar> events = calendarDao.getAllEvents();
        return events.stream().map(CalendarMapper::toDto).collect(Collectors.toList());
    }

    public CalendarDTO getEventById(Integer id) {
        Calendar event = calendarDao.getEventById(id);
        return event != null ? CalendarMapper.toDto(event) : null;
    }

    public CalendarDTO createOrUpdateEvent(CalendarDTO calendarDTO) {
        Calendar calendar = CalendarMapper.toEntity(calendarDTO);
        calendar.setCalendarCreateAt(LocalDateTime.now());
        Calendar savedEvent = calendarDao.saveEvent(calendar);
        return CalendarMapper.toDto(savedEvent);
    }

    public void deleteEvent(Integer id) {
        calendarDao.deleteEvent(id);
    }

}
