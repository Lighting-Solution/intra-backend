package com.ls.in.global.emp.controller;

import com.ls.in.global.emp.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("empController")
@RequestMapping("/api/v1/intranet/contact")
@CrossOrigin(origins = "http://localhost:3000")
public class EmpController {
    @Autowired
    private EmpService empService;

    @GetMapping("/test")
    public void test(@RequestParam("empId") int empId,
                     @RequestParam("positionId") int positionId) {
        empService.getEmpByIdAndDepartmentAndPosition(empId, 9);
        empService.getEmpByPosition(positionId);
    }
}
