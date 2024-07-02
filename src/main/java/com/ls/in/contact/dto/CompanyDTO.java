package com.ls.in.contact.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private int companyId;
    private String companyName;
    private String companyAddress;
    private String companyURL;
    private String companyNumber;
    private String companyFax;
}
