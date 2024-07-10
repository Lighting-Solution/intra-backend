package com.ls.in.contact.controller;

import com.ls.in.contact.dto.ContactAndroidDTO;
import com.ls.in.contact.dto.ContactResponseDTO;
import com.ls.in.contact.dto.PersonalContactDTO;
import com.ls.in.contact.dto.PersonalGroupDTO;
import com.ls.in.global.emp.domain.dto.EmpAndroidDTO;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.domain.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

public interface ContactController {
    /**
     * @apiNote 최조 접속
     * 필요 데이터 :
     * 1. 개인이 만든 모든 주소록 그룹 목록
     * 2. 사내 주소록의 그룹 목록
     * 3. 회사 사원 전체의 연락처
     * @return ContactResponseDTO
     */
    @GetMapping("/list/all/{id}")
    ResponseEntity<ContactResponseDTO> getAll(@PathVariable("id") String empId);

    /**
     * @apiNote 그룹, 검색 필터에 대한 조회
     * @param groupId
     * @param departmentId
     * @param filterType
     * @param filterContent
     * @param sortType
     * @return ContactResponseDTO
     */
    @GetMapping("/list/search")
    ResponseEntity<ContactResponseDTO> getAllBySearch(@RequestParam(name = "empId", required = false) String empId,
                                                      @RequestParam(name = "groupId", required = false) String groupId,
                                                      @RequestParam(name = "departmentId", required = false) String departmentId,
                                                      @RequestParam(name = "filterType", required = false) String filterType,
                                                      @RequestParam(name = "filterContent", required = false) String filterContent,
                                                      @RequestParam(name = "sortType", required = false) String sortType,
                                                      @RequestParam(name = "groupType", required = false) String groupType);

    /**
     * @apiNote 안드로이드 EMP API
     * @return EmpAndroidDTO
     */
    @GetMapping("/list/all-emp/android")
    ResponseEntity<EmpAndroidDTO> getEmpAllByAndroid();

    /**
     * @apiNote 안드로이드 PersonalContact API
     * @param empId
     * @return ContactAndroidDTO
     */
    @GetMapping("/list/all-personal/android/{id}")
    ResponseEntity<ContactAndroidDTO> getPersonalAllByAndroid(@PathVariable("id") String empId);

    /**
     * @apiNote 사내 주소록-전체 주소록 연락처
     * @return List<EmpDTO>
     */
    @GetMapping("/list/all-emp")
    ResponseEntity<List<EmpDTO>> getAllEmp();

    /**
     * @apiNote 개인 주소록-전체 주소록 연락처
     * @param empId
     * @return List<PersonalContactDTO>
     */
    @GetMapping("/list/all-contact/{id}")
    ResponseEntity<List<PersonalContactDTO>> getAllPersonalContact(
            @PathVariable("id") String empId);

    /**
     * @apiNote 사내 주소록-부서 별
     * @param department
     * @return List<EmpDTO>
     */
    @GetMapping("/list/group-emp/{departmentId}")
    ResponseEntity<List<EmpDTO>> getAllEmpByDepartment(@PathVariable("departmentId") String department);

    /**
     * @apiNote 개인 주소록-그룹 주소록 별
     * @param groupId
     * @return List<ContactGroupDTO>
     */
    @GetMapping("/list/group-contact/{groupId}")
    ResponseEntity<List<PersonalContactDTO>> getAllPersonalContactByGroup(
            @PathVariable("groupId") String groupId) throws Exception;

    /**
     * @apiNote 개인 연락처 추가
     * @param requestDTO
     * @return "success" 또는 "fail"
     */
    @PostMapping("/personal-contact")
    ResponseEntity<String> createPersonalContact(@RequestBody PersonalContactDTO requestDTO);

    /**
     * @apiNote 개인 연락처 수정
     * @param requestDTO
     * @return PersonalContactDTO
     */
    @PutMapping("/personal-contact")
    ResponseEntity<PersonalContactDTO> updatePersonalContact(@RequestBody PersonalContactDTO requestDTO);

    /**
     * @apiNote 개인 연락처 삭제
     * @param contactIds
     * @return "success" 또는 "fail"
     */
    @DeleteMapping("/personal-contact")
    ResponseEntity<String> deletePersonalContact(@RequestBody Map<String, Object> contactIds) throws Exception;


    /**
     * @apiNote 개인 주소록 목록 조회
     * @param empId
     * @return List<PersonalGroupDTO>
     */
    @GetMapping("/personal-group/{empId}")
    ResponseEntity<List<PersonalGroupDTO>> getPersonalGroup(@PathVariable("empId") String empId);

    /**
     * @apiNote 개인 그룹 추가
     * @param requestDTO
     * @return "success" 또는 "fail"
     */
    @PostMapping("/personal-group")
    ResponseEntity<String> createPersonalGroup(@RequestBody PersonalGroupDTO requestDTO);

    /**
     * @apiNote 개인 그룹 수정
     * @param requestDTO
     * @return "success" 또는 "fail"
     */
    @PutMapping("/personal-group")
    ResponseEntity<String> updatePersonalGroup(@RequestBody PersonalGroupDTO requestDTO);

    /**
     * @apiNote 개인 그룹 삭제
     * @param groupId
     * @return "success" 또는 "fail"
     */
    @DeleteMapping("/group/{groupId}")
    ResponseEntity<String> deletePersonalGroup(@PathVariable("groupId") String groupId);

    /**
     * @apiNote 개인 연락처를 개인 그룹에 추가
     * @param requestData JSON 형식의 동적 요청 데이터 {"contactId": [1, ...], "groupId": [1, ...]}
     * @return "success" 또는 "fail"
     */
    @PostMapping("/contact-group")
    ResponseEntity<String> createContactGroup(@RequestBody Map<String, Object> requestData) throws Exception;

    /**
     * @apiNote 개인 연락처에서 개인 그룹 지정 해제
     * @param requestData JSON 형식의 동적 요청 데이터 {"contactId": [1, ...], "groupId": [1, ...]}
     * @return "success" 또는 "fail"
     */
    @DeleteMapping("/contact-group")
    ResponseEntity<String> deleteContactGroup(@RequestBody Map<String, Object> requestData) throws Exception;
}
