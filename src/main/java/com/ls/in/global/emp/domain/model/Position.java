package com.ls.in.global.emp.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "position")
public class Position {

    @Id
    @Column(name = "position_id")
    private Integer positionId;

    @Column(name = "name")
    private String positionName;
}
