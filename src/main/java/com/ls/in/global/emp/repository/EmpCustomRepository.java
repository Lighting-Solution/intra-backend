package com.ls.in.global.emp.repository;

import com.ls.in.contact.dto.ContactFilterPageDTO;
import com.ls.in.global.emp.domain.model.Emp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmpCustomRepository {
    Page<Emp> search(ContactFilterPageDTO data);
}
