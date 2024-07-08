package com.ls.in.calendar.service;

import com.ls.in.calendar.domain.model.Participant;
import com.ls.in.calendar.dto.ParticipantDTO;

import java.util.List;

public interface ParticipantService {
    List<ParticipantDTO> getAllParticipants();
    ParticipantDTO getParticipantById(Integer participantId);
    ParticipantDTO createParticipant(Integer empId, Integer calendarId);
}
