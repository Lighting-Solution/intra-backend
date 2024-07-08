package com.ls.in.contact.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contactGroup", uniqueConstraints = {@UniqueConstraint(columnNames = {"personalContact_id", "personalGroup_id"})})
public class ContactGroup {

    @Id
    @Column(name = "contactGroup_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contactGroupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personalGroup_id")
    private PersonalGroup personalGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personalContact_id")
    private PersonalContact personalContact;
}

