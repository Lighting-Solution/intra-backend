package com.ls.in.contact.service;

import com.ls.in.contact.dto.ContactAndroidDTO;
import com.ls.in.contact.dto.ContactFilterDTO;
import com.ls.in.contact.dto.ContactResponseDTO;
import com.ls.in.global.emp.domain.dto.EmpAndroidDTO;

public interface ContactService {
   ContactResponseDTO getAll(int empId);
   EmpAndroidDTO getEmpALlByAndroid();
   ContactAndroidDTO getPersonalAllByAndroid(int empId);
   ContactResponseDTO getAllBySearch(ContactFilterDTO requestDTO);
}
