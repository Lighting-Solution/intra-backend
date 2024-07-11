package com.ls.in.calendar.controller.impl;

import com.ls.in.calendar.controller.CalendarController;
import com.ls.in.calendar.domain.model.Calendar;
import com.ls.in.calendar.dto.CalendarDTO;
import com.ls.in.calendar.dto.ParticipantDTO;
import com.ls.in.calendar.service.CalendarService;
import com.ls.in.calendar.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/lighting_solutions/calendar")
@CrossOrigin(origins = "http://localhost:3000")
public class CalendarControllerImpl implements CalendarController {

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private ParticipantService participantService;

    @GetMapping("/events")
    public List<CalendarDTO> getAllEvents() {
        return calendarService.getAllEvents();
    }

    @GetMapping("/events/{id}")
    public CalendarDTO getEventById(@PathVariable Integer id) {
        return calendarService.getEventById(id);
    }

    @PostMapping("/events")
    public CalendarDTO createEvent(@RequestBody CalendarDTO calendarDTO) {
        if (calendarDTO == null) {
            throw new IllegalArgumentException("CalendarDTO cannot be null");
        }

        System.out.println("Received calendarDTO: " + calendarDTO);

        // Convert the event date from String to LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime start = calendarDTO.getCalendarStartAt();
        LocalDateTime end = calendarDTO.getCalendarEndAt();
        calendarDTO.setCalendarStartAt(start);
        calendarDTO.setCalendarEndAt(end);

        // Create or update the event in the database
        CalendarDTO savedEvent = calendarService.createOrUpdateEvent(calendarDTO);
        System.out.println("================== savedEvent : "+ savedEvent  + "=================");

        // Add participants to the event
        List<ParticipantDTO> attendees = calendarDTO.getAttendees();
        System.out.println(attendees);
        if (attendees != null) {
            for (ParticipantDTO participantDTO : attendees) {
                if (participantDTO.getCalendarDTO() == null) {
                    participantDTO.setCalendarDTO(new CalendarDTO()); // 예외 처리 방지
                }
                participantDTO.getCalendarDTO().setCalendarId(savedEvent.getCalendarId());
                participantService.addParticipantToCalendar(participantDTO);
            }
        }

        return savedEvent;
    }

    @PutMapping("/events/{id}")
    public CalendarDTO updateEvent(@PathVariable Integer id, @RequestBody CalendarDTO calendarDTO) {
        calendarDTO.setCalendarId(id);
        return calendarService.createOrUpdateEvent(calendarDTO);
    }

    @DeleteMapping("/events/{id}")
    public void deleteEvent(@PathVariable Integer id) {
        calendarService.deleteEvent(id);
    }


}
