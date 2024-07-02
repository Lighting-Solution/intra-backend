package com.ls.in.approval.controller.impl;


import com.lowagie.text.DocumentException;
import com.ls.in.approval.controller.DigitalApprovalController;
import com.ls.in.approval.service.DigitalApprovalService;
import com.ls.in.approval.util.LoadHtml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/lighting_solutions")
@CrossOrigin(origins = "http://localhost:3000")
public class DigitalApprovalControllerImpl implements DigitalApprovalController {

    private DigitalApprovalService approvalService;

    private final LoadHtml loadHtml = new LoadHtml();

    @Autowired
    public DigitalApprovalControllerImpl(DigitalApprovalService approvalService){
        this.approvalService = approvalService;
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    // html 양식 가져오기
    @GetMapping("/digital/approval/form")
    public ResponseEntity<String> getHtmlContent(@RequestParam Integer status) {
        String htmlContent = "";
        System.out.println(status);
        switch (status) {
            case 0:
                // 기안문
                htmlContent = loadHtml.load("forms/draftForm.html");
                break;
            case 1:
                // 회의록
                htmlContent = loadHtml.load("forms/meetingForm.html");
                break;
            case 2:
                // 협조문
                htmlContent = loadHtml.load("forms/cooperationForm.html");
                break;
            case 3:
                htmlContent = loadHtml.load("forms/test.html");
            default:
                break;
        }

        return ResponseEntity.ok(htmlContent);
    }

    @PostMapping("/digital/approval/request")
    public ResponseEntity<String> approvalRequest(@RequestBody Map<String, String> request) throws IOException, IOException, DocumentException {
        String status = request.get("status");
        String fontPath = "src/main/resources/fonts/NotoSansKR-Regular.ttf";
        String filePath = "";

        // 서버에 작성한 전자결재 저장하기
        switch (status) {
            case "0":
                // 기안문
                filePath = "src/main/resources/writeForms/draftForm.html";
                loadHtml.save(request, filePath);
                break;
            case "1":
                // 회의록
                filePath = "src/main/resources/writeForms/meetingForm.html";
                loadHtml.save(request, filePath);
                break;
            case "2":
                // 협조문
                filePath = "src/main/resources/writeForms/cooperationForm.html";
                loadHtml.save(request, filePath);
                break;
            case "3":
                filePath = "src/main/resources/writeForms/testForm.html";
                loadHtml.save(request, filePath);
            default:
                break;
        }

        loadHtml.htmlToPdf(filePath, fontPath, request);

        return ResponseEntity.ok("HTML content received and processed successfully");
    }

}
