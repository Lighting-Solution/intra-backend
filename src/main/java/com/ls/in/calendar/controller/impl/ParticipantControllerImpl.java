package com.ls.in.calendar.controller.impl;
import com.ls.in.calendar.controller.ParticipantController;
import com.ls.in.calendar.domain.model.Calendar;
import com.ls.in.calendar.domain.model.Participant;
import com.ls.in.calendar.dto.CalendarDTO;
import com.ls.in.calendar.dto.ParticipantDTO;
import com.ls.in.calendar.repository.CalendarRepository;
import com.ls.in.calendar.repository.ParticipantRepository;
import com.ls.in.calendar.service.ParticipantService;
import com.ls.in.global.emp.domain.dto.EmpByDepartmentDTO;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.exception.EmpNotFoundException;
import com.ls.in.global.emp.repository.EmpRepository;
import com.ls.in.global.emp.service.EmpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lighting_solutions/participant")
@CrossOrigin(origins = "http://localhost:3000")
public class ParticipantControllerImpl implements ParticipantController {

    private final ParticipantService participantService;
    private final EmpService empService;
    private final EmpRepository empRepository;
    private final CalendarRepository calendarRepository;
    private final ParticipantRepository participantRepository;

    public ParticipantControllerImpl(ParticipantService participantService, EmpService empService, EmpRepository empRepository, CalendarRepository calendarRepository, ParticipantRepository participantRepository) {
        this.participantService = participantService;
        this.empService = empService;
        this.empRepository = empRepository;
        this.calendarRepository = calendarRepository;
        this.participantRepository = participantRepository;
    }

    @GetMapping("/departments")
    public ResponseEntity<EmpByDepartmentDTO> getAllParticipantsByDepartment() {
        try {
            EmpByDepartmentDTO responseDTO = empService.getAllByDepartment();
            System.out.println("getAllParticipantsByDepartment : " + responseDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (EmpNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> addParticipantToCalendar(ParticipantDTO participantDTO) {
        System.out.println("par-controller: " +participantDTO);
        try {
            if (participantDTO == null) {
                return ResponseEntity.badRequest().body("ParticipantDTO cannot be null");
            }
            participantService.addParticipantToCalendar(participantDTO);
            System.out.println("par-controller: " +participantDTO);
            EmpByDepartmentDTO responseDTO = empService.getAllByDepartment();
            System.out.println("[[[[[[[[[[["+responseDTO+"]]]]]]]]]]");

            return ResponseEntity.ok("Participant added successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add participant: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }
}