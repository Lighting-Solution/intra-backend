package com.ls.in.global.emp.domain.model;

import com.ls.in.contact.domain.model.Company;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "emp")
public class Emp {

    @Id
    @Column(name = "emp_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer empId;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "account_pw")
    private String accountPw;

    @Column(name = "name")
    private String empName;

    @Column(name = "email")
    private String empEmail;

    @Column(name = "mp")
    private String empMP;

    @Column(name = "memo")
    private String empMemo;

    @Column(name = "hp")
    private String empHP;

    @Column(name = "homeAddress")
    private String empHomeAddress;

    @Column(name = "homeFax")
    private String empHomeFax;

    @Column(name = "birthday")
    private LocalDate empBirthday;

    @Column(name = "sign")
    private String empSign;

    @Column(name = "admin")
    private boolean empAdmin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
}
