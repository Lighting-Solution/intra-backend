package com.ls.in.contact.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactAndroidDTO {
    List<PersonalContactDTO> personalContactDTOList;
    List<PersonalGroupDTO> personalGroupDTOList;
    List<ContactGroupDTO> contactGroupDTOList;
}
