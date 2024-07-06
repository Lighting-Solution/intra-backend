package com.ls.in.messenger.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room")
public class Room {

    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomId;

    @Column(name = "name")
    private String roomName;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    public static Room createRoom(String name) {
        return Room.builder()
                .roomName(name)
                .createdAt(LocalDateTime.now())
                .build();
    }
}