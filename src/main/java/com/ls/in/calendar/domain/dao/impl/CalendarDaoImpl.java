package com.ls.in.calendar.domain.dao.impl;

import com.ls.in.calendar.domain.dao.CalendarDao;
import com.ls.in.calendar.domain.model.Calendar;
import com.ls.in.calendar.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CalendarDaoImpl implements CalendarDao {

    @Autowired
    private CalendarRepository calendarRepository;

    @Override
    public List<Calendar> getAllEvents() {
        return calendarRepository.findAll();
    }

    @Override
    public Calendar getEventById(Integer id) {
        Optional<Calendar> calendar = calendarRepository.findById(id);
        return calendar.orElse(null);
    }

    @Override
    public Calendar saveEvent(Calendar calendar) {
        return calendarRepository.save(calendar);
    }

    @Override
    public void deleteEvent(Integer id) {
        calendarRepository.deleteById(id);
    }
}
