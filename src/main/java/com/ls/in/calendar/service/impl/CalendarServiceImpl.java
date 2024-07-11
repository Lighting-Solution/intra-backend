package com.ls.in.calendar.service.impl;

import com.ls.in.calendar.domain.dao.CalendarDao;
import com.ls.in.calendar.domain.model.Calendar;
import com.ls.in.calendar.dto.CalendarDTO;
import com.ls.in.calendar.service.CalendarService;
import com.ls.in.calendar.util.CalendarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
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

        LocalDateTime startDateTime = calendarDTO.getCalendarStartAt();
        LocalDateTime endDateTime = calendarDTO.getCalendarEndAt();

        Instant startInstant = startDateTime.toInstant(ZoneOffset.UTC);
        Instant endInstant = endDateTime.toInstant(ZoneOffset.UTC);

        ZonedDateTime startUTC = startInstant.atZone(ZoneOffset.UTC);
        ZonedDateTime endUTC = endInstant.atZone(ZoneOffset.UTC);

        // UTC로 설정된 시간을 LocalDateTime으로 변환하여 저장
        calendar.setCalendarStartAt(LocalDateTime.ofInstant(startInstant, ZoneId.systemDefault()));
        calendar.setCalendarEndAt(LocalDateTime.ofInstant(endInstant, ZoneId.systemDefault()));

        calendar.setCalendarCreateAt(LocalDateTime.now());
        Calendar savedEvent = calendarDao.saveEvent(calendar);
        System.out.println("---------------"+savedEvent+"---------------");
        return CalendarMapper.toDto(savedEvent);
    }

    public void deleteEvent(Integer id) {
        calendarDao.deleteEvent(id);
    }

}
