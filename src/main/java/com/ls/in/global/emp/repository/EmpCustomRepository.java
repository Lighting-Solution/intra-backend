package com.ls.in.global.emp.repository;

import com.ls.in.contact.dto.ContactFilterDTO;
import com.ls.in.global.emp.domain.model.Emp;

import java.util.List;

public interface EmpCustomRepository {
    List<Emp> search(ContactFilterDTO data);
}
