package com.ls.in.approval.controller.impl;

import com.lowagie.text.DocumentException;
import com.ls.in.approval.controller.DigitalApprovalController;
import com.ls.in.approval.dto.DigitalApprovalDTO;
import com.ls.in.approval.dto.FormDTO;
import com.ls.in.approval.service.DigitalApprovalService;
import com.ls.in.approval.util.LoadHtml;

import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/lighting_solutions/digital/approval")
@CrossOrigin(origins = "http://localhost:3000")
public class DigitalApprovalControllerImpl implements DigitalApprovalController {

    private final DigitalApprovalService approvalService;

    private final LoadHtml loadHtml = new LoadHtml();

    private EmpService empService;

    @Autowired

    public DigitalApprovalControllerImpl(DigitalApprovalService approvalService, EmpService empService) {
        this.approvalService = approvalService;
        this.empService = empService;
    }

    @Override
    @GetMapping("/form")
    public ResponseEntity<String> getHtmlContent(Integer status) {
        String htmlContent = "";
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

    @Override
    @PostMapping("/request")
    public ResponseEntity<String> approvalRequest(Map<String, String> request) throws IOException, DocumentException {
        Integer empId = Integer.parseInt(request.get("empId")); // 로그인한 사원 ID

        // 사원 정보 가져오기 (직급, 부서)
        EmpDTO empDTO = empService.getEmpById(empId);
        // 양식 상태
        String status = request.get("status");

        String fontPath = "src/main/resources/fonts/NotoSansKR-Regular.ttf";
        String filePath = "";

        // 사용자 결재 서명 추가하기
        String pdfFilePath = "src/main/resources/approvalWaiting/saved_approval.pdf";
        String imagePath = empDTO.getEmpSign();
        String outputPdfPath = "src/main/resources/approvalWaiting/signed"+ empId.toString() +"pdf";

        // html 문서로 부터 데이터 받아오기
        Map<String, String> successResult = new HashMap<>();

        // HTML 들어갈 정보 (FormDTO)
        FormDTO formDTO = new FormDTO();
        formDTO.setDepartment(empDTO.getDepartment().getDepartmentName());
        formDTO.setName(empDTO.getEmpName());
        formDTO.setDigitalApprovalCreatedAt(LocalDateTime.now());

        // 서버에 작성한 전자결재 저장하기
        switch (status) {
            case "0":
                // 기안문
                filePath = "src/main/resources/writeForms/draftForm.html";
                successResult = loadHtml.save(request, filePath, formDTO);
                break;
            case "1":
                // 회의록
                filePath = "src/main/resources/writeForms/meetingForm.html";
                successResult = loadHtml.save(request, filePath, formDTO);
                break;
            case "2":
                // 협조문
                filePath = "src/main/resources/writeForms/cooperationForm.html";
                successResult = loadHtml.save(request, filePath, formDTO);
                break;
        }

        String documentTitle = successResult.get("title");
        DigitalApprovalDTO digitalApprovalDTO = new DigitalApprovalDTO();

        // 전자 결재 테이블 data insert
        digitalApprovalDTO = approvalService.approvalRequest(empId, documentTitle, empDTO);


        // html 파일 PDF 저장
        loadHtml.htmlToPdf(filePath, fontPath, request);

        // PDF 파일 Sign 저장
        LoadHtml.addSignToPDF(pdfFilePath, imagePath, outputPdfPath);

        return ResponseEntity.ok("HTML content received and processed successfully");
    }




    @Override
    @GetMapping("/pdf/{projectName}")
    public ResponseEntity<Resource> getPdf(@PathVariable String projectName) {

        try {
            // 프로젝트 이름을 기반으로 PDF 파일 경로 설정 (이 예시에서는 고정된 경로 사용)
            Path pdfPath = Paths.get("src/main/resources/approvalWaiting/signed_approval.pdf");
            Resource resource = new UrlResource(pdfPath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PDF not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving PDF", e);
        }
    }

    @Override
    @GetMapping("/waiting")
    public ResponseEntity<List<DigitalApprovalDTO>> getApprovalWaitingList(@RequestParam Integer empId) {
        List<DigitalApprovalDTO> digitalApprovalDTOList = approvalService.getApprovalWaitingList(empId);
        return ResponseEntity.ok(digitalApprovalDTOList);
    }


}
