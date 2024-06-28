package com.ls.in.calendar.domain.model;

import com.ls.in.global.emp.domain.model.Emp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "participant")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Integer participantId;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Emp emp;

    @ManyToOne
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;
}
