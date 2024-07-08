package com.ls.in.calendar.service.impl;

import com.ls.in.calendar.domain.model.Calendar;
import com.ls.in.calendar.domain.model.Participant;
import com.ls.in.calendar.dto.ParticipantDTO;
import com.ls.in.calendar.repository.ParticipantRepository;
import com.ls.in.calendar.service.ParticipantService;
import com.ls.in.calendar.util.ParticipantMapper;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.exception.EmpNotFoundException;
import com.ls.in.global.emp.service.EmpService;
import com.ls.in.global.emp.util.EmpMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ls.in.calendar.util.ParticipantMapper.toDto;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;
    private final EmpService empService;

    public ParticipantServiceImpl(ParticipantRepository participantRepository, EmpService empService) {
        this.participantRepository = participantRepository;
        this.empService = empService;
    }

    public List<ParticipantDTO> getAllParticipants() {
        List<Participant> participants = participantRepository.findAll();
        return participants.stream()
                .map(ParticipantMapper::toDto)
                .collect(Collectors.toList());
    }

    public ParticipantDTO getParticipantById(Integer participantId) {
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Participant not found"));
        return toDto(participant);
    }

    public ParticipantDTO createParticipant(Integer empId, Integer calendarId) throws EmpNotFoundException {
        EmpDTO empDTO = empService.getEmpById(empId);
        Emp emp = EmpMapper.toEntity(empDTO);

        Calendar calendar = new Calendar();
        calendar.setCalendarId(calendarId);

        Participant participant = new Participant();
        participant.setEmp(emp);
        participant.setCalendar(calendar);

        participant = participantRepository.save(participant);

        return toDto(participant);
    }

}
