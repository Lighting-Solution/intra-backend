package com.ls.in.contact.controller;

import com.ls.in.contact.dto.*;
import com.ls.in.contact.service.ContactGroupService;
import com.ls.in.contact.service.ContactService;
import com.ls.in.contact.service.PersonalContactService;
import com.ls.in.contact.service.PersonalGroupService;
import com.ls.in.global.emp.domain.dto.EmpAndroidDTO;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.service.ContactEmpService;
import com.ls.in.global.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/intranet/contact")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactControllerImpl implements ContactController {

    private final ContactGroupService contactGroupService;
    private final ContactEmpService contactEmpService;
    private final PersonalContactService personalContactService;
    private final PersonalGroupService personalGroupService;
    private final ContactService contactService;

    @Autowired
    public ContactControllerImpl(ContactGroupService contactGroupService, ContactEmpService contactEmpService, PersonalContactService personalContactService, PersonalGroupService personalGroupService, ContactService contactService) {
        this.contactGroupService = contactGroupService;
        this.contactEmpService = contactEmpService;
        this.personalContactService = personalContactService;
        this.personalGroupService = personalGroupService;
        this.contactService = contactService;
    }


    @GetMapping("/list/all/{id}")
    @Override
    public ResponseEntity<EmpAllResponseDTO> getAll(@PathVariable("id") String empId) {
        EmpAllResponseDTO responseDTO = contactService.getAll(Utils.stringToInteger(empId));
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDTO);
    }

    @GetMapping("/list/search")
    @Override
    public ResponseEntity<ContactResponseDTO> getAllBySearch(@RequestParam(name = "groupId", required = false) String groupId,
                                                             @RequestParam(name = "departmentId", required = false) String departmentId,
                                                             @RequestParam(name = "pageOffset", required = false) String pageOffset,
                                                             @RequestParam(name = "pageSize", required = false) String pageSize,
                                                             @RequestParam(name = "filterType", required = false) String filterType,
                                                             @RequestParam(name = "filterContent", required = false) String filterContent,
                                                             @RequestParam(name = "sortType", required = false) String sortType) {
        ContactFilterPageDTO contactFilterPageDTO = ContactFilterPageDTO.toDTO(
                Utils.stringToInteger(groupId),
                Utils.stringToInteger(departmentId),
                Utils.stringToInteger(pageOffset),
                Utils.stringToInteger(pageSize),
                filterType,
                filterContent,
                sortType);
        if(Utils.checkIntegerNull(contactFilterPageDTO.getGroupId()) && Utils.checkIntegerNull(contactFilterPageDTO.getDepartmentId()))
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "BAD_REQUEST");

        ContactResponseDTO contactResponseDTO = contactService.getAllBySearch(contactFilterPageDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(contactResponseDTO);
    }



    @GetMapping("/list/all-emp/android")
    @Override
    public ResponseEntity<EmpAndroidDTO> getEmpAllByAndroid() {
        EmpAndroidDTO responseDTO = contactService.getEmpALlByAndroid();
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDTO);
    }


    @GetMapping("/list/all-personal/android/{id}")
    @Override
    public ResponseEntity<ContactAndroidDTO> getPersonalAllByAndroid(@PathVariable("id") String empId) {
        ContactAndroidDTO responseDTO = contactService.getPersonalAllByAndroid(Utils.stringToInteger(empId));
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDTO);
    }


    @GetMapping("/list/all-emp")
    @Override
    public ResponseEntity<List<EmpDTO>> getAllEmp() {
        List<EmpDTO> responseList = contactEmpService.getAllEmp();
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseList);
    }


    @GetMapping("/list/all-contact/{id}")
    @Override
    public ResponseEntity<List<PersonalContactDTO>> getAllPersonalContact(
            @PathVariable("id") String empId) {
        List<PersonalContactDTO> resultList = personalContactService.getAllPersonalContact(Utils.stringToInteger(empId));
        return ResponseEntity.status(HttpStatus.OK)
                .body(resultList);
    }


    @GetMapping("/list/group-emp/{departmentId}")
    @Override
    public ResponseEntity<List<EmpDTO>> getAllEmpByDepartment(@PathVariable("departmentId") String department) {
        List<EmpDTO> responseList = contactEmpService.getAllEmpByDepartment(Utils.stringToInteger(department));
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseList);
    }


    @GetMapping("/list/group-contact/{groupId}")
    @Override
    public ResponseEntity<List<PersonalContactDTO>> getAllPersonalContactByGroup(
            @PathVariable("groupId") String groupId) throws Exception {
        ContactFilterPageDTO contactFilterPageDTO = new ContactFilterPageDTO();
        contactFilterPageDTO.setGroupId(Utils.stringToInteger(groupId));
        List<PersonalContactDTO> resultList = contactGroupService.getAllByGroupBySearch(contactFilterPageDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(resultList);
    }


    @PostMapping("/contact")
    @Override
    public ResponseEntity<String> createPersonalContact(@RequestBody PersonalContactDTO requestDTO) {
        boolean result = personalContactService.createPersonalContact(requestDTO);
        if(result) return ResponseEntity.ok("success");
        return ResponseEntity.ok("fail");
    }


    @PutMapping("/contact")
    @Override
    public ResponseEntity<PersonalContactDTO> updatePersonalContact(@RequestBody PersonalContactDTO requestDTO) {
        PersonalContactDTO responseDTO = personalContactService.updatePersonalContact(requestDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDTO);
    }


    @DeleteMapping("/contact")
    @Override
    public ResponseEntity<String> deletePersonalContact(@PathVariable("contactId") int contactId) {
        boolean result = personalContactService.deletePersonalContact(contactId);
        if(result) return ResponseEntity.ok("success");
        return ResponseEntity.ok("fail");
    }


    @PostMapping("/group/{empId}/{groupName}")
    @Override
    public ResponseEntity<String> createPersonalGroup(@PathVariable("empId") String empId,
                                                      @PathVariable("groupName") String groupName) {
        boolean result = personalGroupService.createPersonalGroup(Utils.stringToInteger(empId), groupName);
        if(result) return ResponseEntity.ok("success");
        return ResponseEntity.ok("fail");
    }


    @PutMapping("/group/{groupId}/{groupName}")
    @Override
    public ResponseEntity<String> updatePersonalGroup(@PathVariable("groupId") String groupId,
                                                      @PathVariable("groupName") String groupName) {
        boolean result = personalGroupService.updatePersonalGroup(Utils.stringToInteger(groupId), groupName);
        if(result) return ResponseEntity.ok("success");
        return ResponseEntity.ok("fail");
    }


    @DeleteMapping("/group/{groupId}")
    @Override
    public ResponseEntity<String> deletePersonalGroup(@PathVariable("groupId") String groupId) {
        boolean result = personalGroupService.deletePersonalGroup(Utils.stringToInteger(groupId));
        if(result) return ResponseEntity.ok("success");
        return ResponseEntity.ok("fail");
    }


    @PostMapping("/contact-group")
    @Override
    public ResponseEntity<String> createContactGroup(@RequestBody Map<String, Object> requestData) throws Exception {
        boolean result = contactGroupService.createContactGroup(requestData);
        if(result) return ResponseEntity.ok("success");
        return ResponseEntity.ok("fail");
    }


    @DeleteMapping("/contact-group")
    @Override
    public ResponseEntity<String> deleteContactGroup(@RequestBody Map<String, Object> requestData) throws Exception {
        boolean result = contactGroupService.deleteContactGroup(requestData);
        if(result) return ResponseEntity.ok("success");
        return ResponseEntity.ok("fail");
    }



    /* 페이지, 정렬, 필터 별로 조회 만들어야댐 */
}
