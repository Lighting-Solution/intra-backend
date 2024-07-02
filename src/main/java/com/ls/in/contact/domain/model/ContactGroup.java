package com.ls.in.contact.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "contactGroup")
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

