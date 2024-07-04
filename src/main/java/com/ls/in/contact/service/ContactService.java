package com.ls.in.contact.service;

import com.ls.in.contact.dto.ContactAndroidDTO;
import com.ls.in.contact.dto.ContactFilterPageDTO;
import com.ls.in.contact.dto.ContactResponseDTO;
import com.ls.in.contact.dto.EmpAllResponseDTO;
import com.ls.in.global.emp.domain.dto.EmpAndroidDTO;

public interface ContactService {
   EmpAllResponseDTO getAll(int empId);
   EmpAndroidDTO getEmpALlByAndroid();
   ContactAndroidDTO getPersonalAllByAndroid(int empId);
   ContactResponseDTO getAllBySearch(ContactFilterPageDTO requestDTO);
}
