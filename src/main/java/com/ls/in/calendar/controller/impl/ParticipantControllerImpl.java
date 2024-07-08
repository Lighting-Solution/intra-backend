package com.ls.in.calendar.controller.impl;
import com.ls.in.calendar.controller.ParticipantController;
import com.ls.in.calendar.dto.ParticipantDTO;
import com.ls.in.calendar.service.ParticipantService;
import com.ls.in.global.emp.domain.dto.EmpByDepartmentDTO;
import com.ls.in.global.emp.exception.EmpNotFoundException;
import com.ls.in.global.emp.service.EmpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lighting_solutions/participant")
@CrossOrigin(origins = "http://localhost:3000")
public class ParticipantControllerImpl implements ParticipantController {

    private final ParticipantService participantService;
    private final EmpService empService;

    public ParticipantControllerImpl(ParticipantService participantService, EmpService empService) {
        this.participantService = participantService;
        this.empService = empService;
    }

    @GetMapping("/departments")
    public ResponseEntity<EmpByDepartmentDTO> getAllParticipantsByDepartment() {
        try {
            EmpByDepartmentDTO responseDTO = empService.getAllByDepartment();
            System.out.println("---" +responseDTO + "---");
            return ResponseEntity.ok(responseDTO);
        } catch (EmpNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addParticipantToCalendar(@RequestBody ParticipantDTO participantDTO) {
        try {
            participantService.addParticipantToCalendar(participantDTO);
            return ResponseEntity.ok("Participant added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add participant: " + e.getMessage());
        }
    }

}
