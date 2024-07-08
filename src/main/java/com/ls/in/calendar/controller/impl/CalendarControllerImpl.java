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
        // Convert string dates to LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime startAt = calendarDTO.getCalendarStartAt();
        LocalDateTime endAt = calendarDTO.getCalendarEndAt();

        // Set the converted LocalDateTime back to the DTO
        calendarDTO.setCalendarStartAt(startAt);
        calendarDTO.setCalendarEndAt(endAt);

        System.out.println("======" + calendarDTO + "=======");
        return calendarService.createOrUpdateEvent(calendarDTO);

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
