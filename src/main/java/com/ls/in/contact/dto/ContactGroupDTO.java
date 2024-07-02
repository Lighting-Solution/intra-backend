package com.ls.in.contact.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactGroupDTO {
    private int contactGroupId;
    private PersonalContactDTO personalContact;
    private PersonalGroupDTO personalGroup;
}
