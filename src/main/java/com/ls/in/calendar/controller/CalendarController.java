package com.ls.in.calendar.controller;

import com.ls.in.calendar.dto.CalendarDTO;
import com.ls.in.calendar.dto.ParticipantDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CalendarController {
    List<CalendarDTO> getAllEvents();

    CalendarDTO getEventById(@PathVariable Integer id);

    CalendarDTO createEvent(@RequestBody CalendarDTO calendarDTO);

    CalendarDTO updateEvent(@PathVariable Integer id, @RequestBody CalendarDTO calendarDTO);

    void deleteEvent(@PathVariable Integer id);


}
