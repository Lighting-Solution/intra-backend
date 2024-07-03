package com.ls.in.contact.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ls.in.global.emp.domain.model.Emp;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "personalGroup")
public class PersonalGroup {

    @Id
    @Column(name = "personalGroup_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer personalGroupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id")
    private Emp emp;

    @Column(name = "name")
    private String personalGroupName;

    @OneToMany(mappedBy = "personalGroup", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ContactGroup> contactGroups;
}
