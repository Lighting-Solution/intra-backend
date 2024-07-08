package com.ls.in.approval.domain.model;

import com.ls.in.global.emp.domain.model.Emp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "digitalApproval")
public class DigitalApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "digitalApproval_id")
    private Integer digitalApprovalId;

    @Column(name = "drafter_id")
    private Integer drafterId;

    @Column(name = "name")
    private String digitalApprovalName;

    @Column(name = "path")
    private String digitalApprovalPath;

    @Column(name = "type")
    private boolean digitalApprovalType;

    @Column(name = "drafterStatus")
    private boolean drafterStatus;

    @Column(name = "managerStatus")
    private boolean managerStatus;

    @Column(name = "ceoStatus")
    private boolean ceoStatus;

    @Column(name = "createdAt")
    private LocalDateTime digitalApprovalCreateAt;

    @Column(name ="approvalAt")
    private LocalDateTime digitalApprovalAt;

    @Column(name ="managerRejectAt")
    private LocalDateTime managerRejectAt;

    @Column(name ="ceoRejectAt")
    private LocalDateTime ceoRejectAt;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Emp emp;
}
