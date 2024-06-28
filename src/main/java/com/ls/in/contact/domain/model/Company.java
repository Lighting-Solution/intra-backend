package com.ls.in.contact.domain.model;

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
@Table(name = "company")
public class Company {

    @Id
    @Column(name = "company_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;

    @Column(name = "name")
    private String companyName;

    @Column(name = "address")
    private String companyAddress;

    @Column(name = "URL")
    private String companyURL;

    @Column(name = "number")
    private String companyNumber;

    @Column(name = "fax")
    private String companyFax;
}
