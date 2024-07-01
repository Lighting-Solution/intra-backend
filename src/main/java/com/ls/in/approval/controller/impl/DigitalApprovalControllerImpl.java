package com.ls.in.approval.controller.impl;

import com.ls.in.approval.controller.DigitalApprovalController;
import com.ls.in.approval.service.DigitalApprovalService;
import com.ls.in.approval.util.LoadHtml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/lighting_solutions")
@CrossOrigin(origins = "http://localhost:3000")
public class DigitalApprovalControllerImpl implements DigitalApprovalController {

    private DigitalApprovalService approvalService;
    private final LoadHtml loadHtml = new LoadHtml();

    @Autowired
    public DigitalApprovalControllerImpl(DigitalApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    // html 양식 가져오기
    @GetMapping("/form")
    public ResponseEntity<String> getHtmlContent(@RequestParam Integer status) {
        String htmlContent = "";

        switch (status) {
            case 0:
                // 기안문
                htmlContent = loadHtml.load("forms/draftForm.html");
                System.out.println("기안문" + status);
                break;
            case 1:
                // 회의록
                htmlContent = loadHtml.load("forms/meetingForm.html");
                System.out.println("회의록" + status);
                break;
            case 2:
                // 협조문
                htmlContent = loadHtml.load("forms/cooperationForm.html");
                System.out.println("협조문" + status);
                break;
            default:
                // 다른 상황 처리
                htmlContent = "Not found";
                break;
        }

        return ResponseEntity.ok(htmlContent);
    }


    // 결재 요청
    @PostMapping("/approval/request")
    public ResponseEntity<String> approvalRequest(@RequestBody Map<String, String> request) {
        String status = request.get("status");
        System.out.println(status);

        // 서버에 작성한 전자결재 저장하기
        switch (status) {
            case "0" ->
                // 기안문
                    loadHtml.save(request, "src/main/resources/writeForms/draftForm.html");
            case "1" ->
                // 회의록
                    loadHtml.save(request, "src/main/resources/writeForms/meetingForm.html");
            case "2" ->
                // 협조문
                    loadHtml.save(request, "src/main/resources/writeForms/cooperationForm.html");
        }


        // html -> pdf 변경하기 (결재진행중 저장)



        return ResponseEntity.ok("HTML content received and processed successfully");
    }

// 예시 메소드: 부서명을 조회하는 메소드
    private String getDepartmentName() {
        // 여기에 실제 데이터베이스에서 부서명을 조회하는 로직을 구현
        return "영업부";
    }

    //예시 메소드: 이름 조회하는 메소드
    private String getName() {
        return "냥냥냥";
    }
}
    /*
    // 결재 요청
    @GetMapping("/approval/request")
    public ResponseEntity<String> getApprovalRequest() {
        Map<String, String> data = new HashMap<>();
        data.put("createdDate", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));

        try {
            Resource resource = new ClassPathResource("forms/draftForm.html");
            String htmlContent = new String(Files.readAllBytes(resource.getFile().toPath()));

            // 데이터를 HTML에 삽입
            for (Map.Entry<String, String> entry : data.entrySet()) {
                htmlContent = htmlContent.replace("${" + entry.getKey() + "}", entry.getValue());
            }
            // 부서명 추가 로직
            String departmentName = getDepartmentName(); // 예시: 실제 데이터베이스에서 부서명을 조회하는 로직 필요

            // 이름 추가 로직
            String name = getName();

            htmlContent = htmlContent.replace("${departmentName}", departmentName);
            htmlContent = htmlContent.replace( "${name}", name);
            return ResponseEntity.ok(htmlContent);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error loading the HTML content");
        }
    }
}

     */
