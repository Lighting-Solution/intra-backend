package com.ls.in.global.emp.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "department")
public class Department {

    @Id
    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "name")
    private String departmentName;
}
