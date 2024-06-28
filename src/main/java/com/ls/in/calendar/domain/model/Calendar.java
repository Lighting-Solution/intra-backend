package com.ls.in.calendar.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private Integer calendarTitle;

    @Column(name = "createAt")
    private LocalDateTime calendarCreateAt;

    @Column(name = "content")
    private String calendarContent;

    @Column(name = "startAt")
    private LocalDateTime calendarStartAt;

    @Column(name = "endAt")
    private LocalDateTime calendarEndAt;
}
