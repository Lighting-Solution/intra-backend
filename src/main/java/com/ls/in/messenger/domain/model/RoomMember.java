package com.ls.in.messenger.domain.model;

import com.ls.in.global.emp.domain.model.Emp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roomMember")
public class RoomMember {

    @Id
    @Column(name = "roomMember_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id")
    private Emp emp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    private Boolean presentStatus;
    private Boolean notificationStatus;

    public static RoomMember createRoomMember(Emp emp, Room room) {
        return RoomMember.builder()
                .emp(emp)
                .room(room)
                .presentStatus(true)
                .notificationStatus(false)
                .build();
    }

    public void updatePresentStatusFalse(){
        this.presentStatus = false;
    }

    public void updateNotificationStatusTrue() {
        this.notificationStatus = true;
    }
    public void updateNotificationStatusFalse() {
        this.notificationStatus = false;
    }
}