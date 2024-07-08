package com.ls.in.calendar.service.impl;

import com.ls.in.calendar.domain.model.Calendar;
import com.ls.in.calendar.domain.model.Participant;
import com.ls.in.calendar.dto.ParticipantDTO;
import com.ls.in.calendar.repository.CalendarRepository;
import com.ls.in.calendar.repository.ParticipantRepository;
import com.ls.in.calendar.service.ParticipantService;

import com.ls.in.calendar.util.CalendarMapper;
import com.ls.in.global.emp.domain.dao.EmpDAO;
import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.util.EmpMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantRepository participantRepository;
    private final CalendarRepository calendarRepository;


    public ParticipantServiceImpl(ParticipantRepository participantRepository, CalendarRepository calendarRepository) {
        this.participantRepository = participantRepository;
        this.calendarRepository = calendarRepository;
    }


    @Override
    public void addParticipantToCalendar(ParticipantDTO participantDTO) {
        try {
            // Map CalendarDTO to Calendar entity
            Calendar calendar = CalendarMapper.toEntity(participantDTO.getCalendarDTO());

            // Save Calendar entity
            Calendar calendar1 = calendarRepository.save(calendar);
            Integer calendarId = calendar1.getCalendarId();

            // List<Integer> empIdList = [1,2,3];
            /*
            for(Integer empId : empIdList){
                Participant participant = new Participant();
                participant.setCalendar();
                participant.setEmp();
            }
*/
            // Map ParticipantDTO to Participant entity
            Participant participant = new Participant();
            participant.setParticipantId(participantDTO.getParticipantId());

            Emp emp = EmpMapper.toEntity(participantDTO.getEmpDTO());
            participant.setEmp(emp);

            // Set the previously saved Calendar entity to Participant
            participant.setCalendar(calendar);

            // Save Participant entity
            participantRepository.save(participant);

            System.out.println("Participant added successfully: " + participant);
        } catch (Exception e) {
            System.err.println("Failed to add participant: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
