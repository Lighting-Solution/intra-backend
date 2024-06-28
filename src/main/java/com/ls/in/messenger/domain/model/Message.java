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
@Table(name = "message")
public class Message {

    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer messageId;

    @Column(name = "content")
    private String messageContent;

    @Column(name = "sendTime")
    private LocalDateTime messageSendTime;

    @ManyToOne
    @JoinColumn(name = "roomMember_id", nullable = false)
    private RoomMember roomMember;
}