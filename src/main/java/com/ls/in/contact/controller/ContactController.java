package com.ls.in.contact.controller;

import com.ls.in.contact.dto.ContactAllResponseDTO;
import com.ls.in.contact.dto.ContactGroupDTO;
import com.ls.in.contact.dto.ContactRequestDTO;
import com.ls.in.contact.service.ContactGroupService;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/intranet/contact")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactController {

    private final ContactGroupService contactGroupService;
    private final EmpService empService;

    @Autowired
    public ContactController(ContactGroupService contactGroupService, EmpService empService) {
        this.contactGroupService = contactGroupService;
        this.empService = empService;
    }

    @GetMapping("/my-contact/{id}")
    public ResponseEntity<ContactAllResponseDTO> getAllContact(@PathVariable("id") int id) {
        List<ContactGroupDTO> resultGroup =  contactGroupService.getAllContact(id);
        List<EmpDTO> resultEmp = empService.getAllEmp();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ContactAllResponseDTO(resultGroup, resultEmp));
    }

    @PostMapping("/new-personal-contact")
    public String createPersonalContact(@RequestBody ContactRequestDTO requestDTO) {

        return "success";
    }
}
