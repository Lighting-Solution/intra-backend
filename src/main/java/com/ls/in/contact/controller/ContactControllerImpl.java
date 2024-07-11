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
@RequestMapping("/api/v1/lighting_solutions/contact")
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
    public ResponseEntity<ContactResponseDTO> getAll(@PathVariable("id") String empId) {
        ContactResponseDTO responseDTO = contactService.getAll(Utils.stringToInteger(empId));
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDTO);
    }

    @GetMapping("/list/search")
    @Override
    public ResponseEntity<ContactResponseDTO> getAllBySearch(@RequestParam(name = "empId", required = false) String empId,
                                                             @RequestParam(name = "groupId", required = false) String groupId,
                                                             @RequestParam(name = "departmentId", required = false) String departmentId,
                                                             @RequestParam(name = "filterType", required = false) String filterType,
                                                             @RequestParam(name = "filterContent", required = false) String filterContent,
                                                             @RequestParam(name = "sortType", required = false) String sortType,
                                                             @RequestParam(name = "groupType", required = false) String groupType) {
        ContactFilterDTO contactFilterDTO = ContactFilterDTO.toDTO(
                Utils.stringToInteger(groupId),
                Utils.stringToInteger(departmentId),
                filterType,
                filterContent,
                sortType,
                Utils.stringToInteger(empId),
                groupType);
        System.out.println(contactFilterDTO.toString());
        ContactResponseDTO contactResponseDTO = contactService.getAllBySearch(contactFilterDTO);
        if(contactResponseDTO != null) System.out.println(contactResponseDTO.toString());
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
        ContactFilterDTO contactFilterDTO = new ContactFilterDTO();
        contactFilterDTO.setGroupId(Utils.stringToInteger(groupId));
        List<PersonalContactDTO> resultList = contactGroupService.getAllByGroupBySearch(contactFilterDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(resultList);
    }


    @PostMapping("/personal-contact")
    @Override
    public ResponseEntity<String> createPersonalContact(@RequestBody PersonalContactDTO requestDTO) {
        boolean result = personalContactService.createPersonalContact(requestDTO);
        if(result) return ResponseEntity.ok("success");
        return ResponseEntity.ok("fail");
    }


    @PutMapping("/personal-contact")
    @Override
    public ResponseEntity<PersonalContactDTO> updatePersonalContact(@RequestBody PersonalContactDTO requestDTO) {
        PersonalContactDTO responseDTO = personalContactService.updatePersonalContact(requestDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDTO);
    }

    @DeleteMapping("/personal-contact")
    @Override
    public ResponseEntity<String> deletePersonalContact(@RequestBody Map<String, Object> contactIds) {
        boolean result = personalContactService.deletePersonalContacts(contactIds);
        if(result) return ResponseEntity.ok("success");
        return ResponseEntity.ok("fail");
    }

    @GetMapping("/personal-group/{empId}")
    @Override
    public ResponseEntity<List<PersonalGroupDTO>> getPersonalGroup(@PathVariable("empId") String empId) {
        List<PersonalGroupDTO> resultList = personalGroupService.getAllByEmp(Utils.stringToInteger(empId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(resultList);
    }

    @PostMapping("/personal-group")
    @Override
    public ResponseEntity<String> createPersonalGroup(@RequestBody PersonalGroupDTO requestDTO) {
        boolean result = personalGroupService.createPersonalGroup(requestDTO.getEmpId(), requestDTO.getPersonalGroupName());
        if(result) return ResponseEntity.ok("success");
        return ResponseEntity.ok("fail");
    }


    @PutMapping("/personal-group")
    @Override
    public ResponseEntity<String> updatePersonalGroup(@RequestBody PersonalGroupDTO requestDTO) {
        boolean result = personalGroupService.updatePersonalGroup(requestDTO.getEmpId(), requestDTO.getPersonalGroupName());
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

}
