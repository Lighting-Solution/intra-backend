package com.ls.in.approval.controller.impl;


import com.lowagie.text.DocumentException;
import com.ls.in.approval.controller.DigitalApprovalController;
import com.ls.in.approval.service.DigitalApprovalService;
import com.ls.in.approval.util.LoadHtml;

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
import java.util.Map;


@RestController
@RequestMapping("/api/v1/lighting_solutions/digital/approval")
@CrossOrigin(origins = "http://localhost:3000")
public class DigitalApprovalControllerImpl implements DigitalApprovalController {

    private DigitalApprovalService approvalService;

    private final LoadHtml loadHtml = new LoadHtml();

    @Autowired
    public DigitalApprovalControllerImpl(DigitalApprovalService approvalService){
        this.approvalService = approvalService;
    }

     /**
     * @apiNote resources에 저장되어 있는 html 폼 가져오기
     * @param status
     * @return
     */
    @GetMapping("/form")
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

    /**
     * @apiNote 사용자가 작성한 전재결재 저장하고, pdf 변환하고 해당 사용자의 sign pdf에 저장
     * @param request
     * @return
     */
    @PostMapping("/request")
    public ResponseEntity<String> approvalRequest(@RequestBody Map<String, String> request) throws IOException, DocumentException {
        // 양식 상태
        String status = request.get("status");

        String fontPath = "src/main/resources/fonts/NotoSansKR-Regular.ttf";
        String filePath = "";

        // 사용자 결재 서명 추가하기
        String pdfFilePath = "src/main/resources/approvalWaiting/saved_approval.pdf";
        String imagePath = "src/main/resources/signs/sign3.png";
        String outputPdfPath = "src/main/resources/approvalWaiting/signed_approval.pdf";

        //서버에 작성한 전자결재 저장하기
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
        }

        // html 파일 PDF 저장
        loadHtml.htmlToPdf(filePath, fontPath, request);

        // PDF 파일 Sign 저장
        LoadHtml.addSignToPDF(pdfFilePath, imagePath, outputPdfPath);

        return ResponseEntity.ok("HTML content received and processed successfully");
    }


    /**
     * @apiNote PDF Viewer Api 를 활용해서 PDF 화면 띄우기
     * @param projectName
     * @return
     */
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

}
