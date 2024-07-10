package com.ls.in.calendar.service;

import com.ls.in.calendar.domain.model.Participant;
import com.ls.in.calendar.dto.ParticipantDTO;

import java.util.List;

public interface ParticipantService {

    void addParticipantToCalendar(ParticipantDTO participantDTO);
}
