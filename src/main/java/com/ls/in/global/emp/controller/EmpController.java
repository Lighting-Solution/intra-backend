package com.ls.in.global.emp.controller;

import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.domain.dto.UserDTO;
import com.ls.in.global.emp.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("empController")
@RequestMapping("/api/v1/intranet/emp")
@CrossOrigin(origins = "http://localhost:3000")
public class EmpController {
    @Autowired
    private EmpService empService;


    @PostMapping("/toIntra")
    public ResponseEntity<UserDTO> securityToIntra(@RequestBody UserDTO userDTO){
        System.out.println("intra : " + userDTO.getAccountId());
        // userName으로 accountId값이랑 동일한 값 찾아서 accountId와 accountPW 반환
        UserDTO intraUserDTO = empService.findByAccountId(userDTO.getAccountId());
        return ResponseEntity.status(HttpStatus.OK).body(intraUserDTO);
    }

    @GetMapping("/toMessenger")
    public ResponseEntity<List<EmpDTO>> empToMessenger() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(empService.getAllEmp());
    }
}
