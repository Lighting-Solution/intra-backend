package com.ls.in.calendar.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "calendar")
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_id")
    private Integer calendarId;

    @Column(name = "title")
    private String calendarTitle;

    @Column(name = "createAt")
    private LocalDateTime calendarCreateAt;

    @Column(name = "content")
    private String calendarContent;

    @Column(name = "startAt")
    private LocalDateTime calendarStartAt;

    @Column(name = "endAt")
    private LocalDateTime calendarEndAt;

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> attendees;
}
