package com.ls.in.calendar.controller;

import com.ls.in.calendar.dto.ParticipantDTO;
import com.ls.in.global.emp.domain.dto.EmpByDepartmentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface ParticipantController {
    ResponseEntity<EmpByDepartmentDTO> getAllParticipantsByDepartment();
    ResponseEntity<String> addParticipantToCalendar(@RequestBody ParticipantDTO participantDTO);
}

