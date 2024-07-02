package com.ls.in.contact.controller;

import com.ls.in.contact.dto.ContactGroupDTO;
import com.ls.in.contact.dto.ContactRequestDTO;
import com.ls.in.contact.dto.PersonalContactDTO;
import com.ls.in.contact.service.ContactGroupService;
import com.ls.in.contact.service.PersonalContactService;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.service.ContactEmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/intranet/contact")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactController {

    private final ContactGroupService contactGroupService;
    private final ContactEmpService contactEmpService;
    private final PersonalContactService personalContactService;

    @Autowired
    public ContactController(ContactGroupService contactGroupService, ContactEmpService contactEmpService, PersonalContactService personalContactService) {
        this.contactGroupService = contactGroupService;
        this.contactEmpService = contactEmpService;
        this.personalContactService = personalContactService;
    }

/*    @GetMapping("/my-contact/{id}")
    public ResponseEntity<ContactAllResponseDTO> getAllContact(@PathVariable("id") int id) {
        List<ContactGroupDTO> resultGroup =  contactGroupService.getAllContact(id);
        List<EmpDTO> resultEmp = empService.getAllEmp();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ContactAllResponseDTO(resultGroup, resultEmp));
    }*/

    /**
     * @apiNote 최조 접속 및 공용 주소록-전체 주소록
     * @return
     */
    @GetMapping("/list/all-emp")
    public ResponseEntity<List<EmpDTO>> getAllEmp() {
        List<EmpDTO> responseList = contactEmpService.getAllEmp();
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseList);
    }

    /**
     * @apiNote 개인 주소록-전체 주소록
     * @return
     */
    @GetMapping("/list/all-personal-contact")
    public ResponseEntity<List<PersonalContactDTO>> getAllPersonalContact() {
        List<PersonalContactDTO> resultList = personalContactService.getAllPersonalContact();
        return ResponseEntity.status(HttpStatus.OK)
                .body(resultList);
    }

    /**
     * @apiNote 공용 주소록-부서 별
     * @param department
     * @return
     */
    @GetMapping("/list/group-emp")
    public ResponseEntity<List<EmpDTO>> getAllEmpByDepartment(@RequestParam("department") int department) {
        List<EmpDTO> responseList = contactEmpService.getAllEmpByDepartment(department);
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseList);
    }

    /**
     * @apiNote 개인 주소록-그룹 주소록 별
     * @param empId
     * @param groupId
     * @return
     */
    @GetMapping("/list/group-personal-contact")
    public ResponseEntity<List<ContactGroupDTO>> getAllPersonalContactByGroup(
            @RequestParam("empId") int empId,
            @RequestParam("personalGroupId") int groupId) {
        List<ContactGroupDTO> resultList = contactGroupService.getAllPersonalContactByGroup(empId, groupId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(resultList);
    }

    @PostMapping("/new-personal-contact")
    public String createPersonalContact(@RequestBody ContactRequestDTO requestDTO) {

        return "success";
    }
}
