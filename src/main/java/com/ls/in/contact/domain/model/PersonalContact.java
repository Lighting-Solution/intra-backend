package com.ls.in.contact.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ls.in.global.emp.domain.model.Emp;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "personalContact")
public class PersonalContact {

    @Id
    @Column(name = "personalContact_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer personalContactId;

    @Column(name = "positionName")
    private String positionName;

    @Column(name = "departmentName")
    private String departmentName;

    @Column(name = "name")
    private String personalContactName;

    @Column(name = "nickName")
    private String personalContactNickName;

    @Column(name = "email")
    private String personalContactEmail;

    @Column(name = "mp")
    private String personalContactMP;

    @Column(name = "memo")
    private String personalContactMemo;

    @Column(name = "birthday")
    private LocalDate personalContactBirthday;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id")
    private Emp emp;

    @OneToMany(mappedBy = "personalContact", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ContactGroup> contactGroups;
}
