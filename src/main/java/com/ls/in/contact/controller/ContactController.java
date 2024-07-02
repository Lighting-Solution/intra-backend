package com.ls.in.contact.controller;

import com.ls.in.contact.dto.ContactGroupDTO;
import com.ls.in.contact.dto.PersonalContactDTO;
import com.ls.in.contact.service.ContactGroupService;
import com.ls.in.contact.service.PersonalContactService;
import com.ls.in.contact.service.PersonalGroupService;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.service.ContactEmpService;
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
    private final ContactEmpService contactEmpService;
    private final PersonalContactService personalContactService;
    private final PersonalGroupService personalGroupService;

    @Autowired
    public ContactController(ContactGroupService contactGroupService, ContactEmpService contactEmpService, PersonalContactService personalContactService, PersonalGroupService personalGroupService) {
        this.contactGroupService = contactGroupService;
        this.contactEmpService = contactEmpService;
        this.personalContactService = personalContactService;
        this.personalGroupService = personalGroupService;
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
    @GetMapping("/list/group-emp/{departmentId}")
    public ResponseEntity<List<EmpDTO>> getAllEmpByDepartment(@PathVariable("departmentId") int department) {
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
    @GetMapping("/list/group-personal-contact/{empId}/{personalGroupId}")
    public ResponseEntity<List<ContactGroupDTO>> getAllPersonalContactByGroup(
            @PathVariable("empId") int empId,
            @PathVariable("personalGroupId") int groupId) {
        List<ContactGroupDTO> resultList = contactGroupService.getAllPersonalContactByGroup(empId, groupId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(resultList);
    }

    /**
     * 개인 연락처 추가
     * @param requestDTO
     * @return
     */
    @PostMapping("/personal-contact")
    public String createPersonalContact(@RequestBody PersonalContactDTO requestDTO) {
        boolean result = personalContactService.createPersonalContact(requestDTO);
        if(result) return "success";
        return "fail";
    }

    /**
     * 개인 연락처 수정
     * @param requestDTO
     * @return
     */
    @PutMapping("/personal-contact")
    public ResponseEntity<PersonalContactDTO> updatePersonalContact(@RequestBody PersonalContactDTO requestDTO) {
        PersonalContactDTO responseDTO = personalContactService.updatePersonalContact(requestDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDTO);
    }

    /**
     * @apiNote 개인 연락처 삭제
     * @param contactId
     * @return
     */
    @DeleteMapping("/personal-contact")
    public String deletePersonalContact(@PathVariable("contactId") int contactId) {
        boolean result = personalContactService.deletePersonalContact(contactId);
        if(result) return "success";
        return "fail";
    }

    /**
     * @apiNote 개인 그룹 추가
     * @param empId
     * @param groupName
     * @return
     */
    @GetMapping("/group/{empId}/{groupName}")
    public String createPersonalGroup(@PathVariable("empId") int empId,
                                      @PathVariable("groupName") String groupName) {
        boolean result = personalGroupService.createPersonalGroup(empId, groupName);
        if(result) return "success";
        return "fail";
    }

    /**
     * @apiNote 개인 그룹 수정
     * @param groupId
     * @param groupName
     * @return
     */
    @PutMapping("/group/{groupId}/{groupName}")
    public String updatePersonalGroup(@PathVariable("groupId") int groupId,
                                      @PathVariable("groupName") String groupName) {
        boolean result = personalGroupService.updatePersonalGroup(groupId, groupName);
        if(result) return "success";
        return "fail";
    }

    /**
     * @apiNote 개인 그룹 삭제
     * @param groupId
     * @return
     */
    @DeleteMapping("/group/{groupId}")
    public String deletePersonalGroup(@PathVariable("groupId") int groupId) {
        boolean result = personalGroupService.deletePersonalGroup(groupId);
        if(result) return "success";
        return "fail";
    }

    /* 페이지, 정렬, 필터 별로 조회 만들어야댐 */
}
