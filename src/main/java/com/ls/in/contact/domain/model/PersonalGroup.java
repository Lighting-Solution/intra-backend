package com.ls.in.contact.domain.model;

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


}
