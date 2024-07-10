package com.ls.in.calendar.service.impl;

import com.ls.in.calendar.domain.model.Calendar;
import com.ls.in.calendar.domain.model.Participant;
import com.ls.in.calendar.dto.CalendarDTO;
import com.ls.in.calendar.dto.ParticipantDTO;
import com.ls.in.calendar.repository.CalendarRepository;
import com.ls.in.calendar.repository.ParticipantRepository;
import com.ls.in.calendar.service.CalendarService;
import com.ls.in.calendar.service.ParticipantService;

import com.ls.in.calendar.util.CalendarMapper;
import com.ls.in.global.emp.domain.dto.EmpByDepartmentDTO;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.repository.EmpRepository;
import com.ls.in.global.emp.service.EmpService;
import com.ls.in.global.emp.service.impl.EmpServiceImpl;
import com.ls.in.global.emp.util.EmpMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantRepository participantRepository;
    private final CalendarRepository calendarRepository;
    private final EmpRepository empRepository;
    private final EmpService empService;
    private final CalendarService calendarService;

    public ParticipantServiceImpl(ParticipantRepository participantRepository, CalendarRepository calendarRepository, EmpRepository empRepository, EmpServiceImpl empService, CalendarService calendarService) {
        this.participantRepository = participantRepository;
        this.calendarRepository = calendarRepository;
        this.empRepository = empRepository;
        this.empService = empService;
        this.calendarService = calendarService;
    }

    @Override
    @Transactional
    public void addParticipantToCalendar(ParticipantDTO participantDTO) {
        System.out.println("par-service: " + participantDTO);

        // Retrieve all employees by department
        EmpByDepartmentDTO empByDepartmentDTO = empService.getAllByDepartment();

        // Extract department name and employee ID from participantDTO
        String departmentName = participantDTO.getEmpDTO().getDepartment().getDepartmentName();
        Integer empId = participantDTO.getEmpDTO().getEmpId();

        // Get the list of employees for the given department
        List<EmpDTO> employees = getEmployeeListByDepartment(empByDepartmentDTO, departmentName);

        // Check if the employee exists in the list
        for (EmpDTO employee : employees) {
            if (employee.getEmpId()==empId) {
                // Create and save the participant entity
                Participant participant = new Participant();

                // Retrieve EmpDTO by ID and convert to Emp entity
                EmpDTO empDTO = empService.getEmpById(empId);
                if (empDTO != null) {
                    Emp emp = EmpMapper.toEntity(empDTO);
                    participant.setEmp(emp);
                } else {
                    System.err.println("Error: Employee not found for ID: " + empId);
                    break;
                }

                // Retrieve CalendarDTO by ID and convert to Calendar entity
                CalendarDTO calendarDTO = calendarService.getEventById(participantDTO.getCalendarDTO().getCalendarId());
                if (calendarDTO != null) {
                    Calendar calendar = CalendarMapper.toEntity(calendarDTO);
                    participant.setCalendar(calendar);
                } else {
                    System.err.println("Error: Calendar event not found for ID: " + participantDTO.getCalendarDTO().getCalendarId());
                    break;
                }

                // Save the participant entity
                participantRepository.save(participant);
                break;
            }
        }
    }

    private List<EmpDTO> getEmployeeListByDepartment(EmpByDepartmentDTO empByDepartmentDTO, String departmentName) {
        switch (departmentName) {
            case "sbd_webService":
                return empByDepartmentDTO.getSbd_webService();
            case "sbd_consultationService":
                return empByDepartmentDTO.getSbd_consultationService();
            case "msd_hello":
                return empByDepartmentDTO.getMsd_hello();
            case "msd_money":
                return empByDepartmentDTO.getMsd_money();
            case "msd_sales":
                return empByDepartmentDTO.getMsd_sales();
            case "개발 1팀":
                return empByDepartmentDTO.getSdd_development_1();
            case "sdd_development_2":
                return empByDepartmentDTO.getSdd_development_2();
            case "sdd_engineer_1":
                return empByDepartmentDTO.getSdd_engineer_1();
            case "sdd_engineer_2":
                return empByDepartmentDTO.getSdd_engineer_2();
            case "sdd_design":
                return empByDepartmentDTO.getSdd_design();
            default:
                throw new IllegalArgumentException("Unknown department: " + departmentName);
        }
    }



}

