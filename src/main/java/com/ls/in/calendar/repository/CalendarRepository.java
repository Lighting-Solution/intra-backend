package com.ls.in.calendar.repository;

import com.ls.in.calendar.domain.model.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Integer> {
}
