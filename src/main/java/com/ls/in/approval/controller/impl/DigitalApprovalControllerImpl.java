package com.ls.in.approval.controller.impl;

import com.lowagie.text.DocumentException;
import com.ls.in.approval.controller.DigitalApprovalController;
import com.ls.in.approval.dto.CeoDTO;
import com.ls.in.approval.dto.DigitalApprovalDTO;
import com.ls.in.approval.dto.FormDTO;
import com.ls.in.approval.dto.ManagerDTO;
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

    private Integer documentCount = 1;

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

        // empId와 일치하는 사원 정보 가져오기 (직급, 부서)
        EmpDTO empDTO = empService.getEmpById(empId);

        // HTML 들어갈 정보 (FormDTO)
        FormDTO formDTO = new FormDTO();
        formDTO.setDepartment(empDTO.getDepartment().getDepartmentName());

        formDTO.setPosition(empDTO.getPosition().getPositionName());
        formDTO.setName(empDTO.getEmpName());
        formDTO.setDigitalApprovalCreatedAt(LocalDateTime.now());

        // empId와 일치하는 사원의 부서의 특정 직급과 일치하는 사원 정보 (직급, 이름) - 부장
        EmpDTO empDTO1 = empService.getEmpByIdAndDepartmentAndPosition(empId, 2);
        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setName(empDTO1.getEmpName());
        managerDTO.setPosition(empDTO1.getPosition().getPositionName());
        managerDTO.setDepartment(empDTO1.getPosition().getPositionName());

        // position의 id가 일치하는 사원의 정보 - CEO
        EmpDTO empDTO2 = empService.getEmpByPosition(1);
        CeoDTO ceoDTO = new CeoDTO();
        ceoDTO.setName(empDTO2.getEmpName());
        ceoDTO.setPosition(empDTO2.getPosition().getPositionName());

        // 양식 상태
        String status = request.get("status");

        String fontPath = "src/main/resources/fonts/NotoSansKR-Regular.ttf";
        String filePath = "";

        // 사용자 결재 서명 추가하기
        String pdfFilePath = "src/main/resources/approvalWaiting/saved_approval.pdf";
        String imagePath = empDTO.getEmpSign();

        // html 문서로 부터 데이터 받아오기
        Map<String, String> successResult = new HashMap<>();

        // 서버에 작성한 전자결재 저장하기
        switch (status) {
            case "0":
                // 기안문
                filePath = "src/main/resources/writeForms/draftForm.html";
                successResult = loadHtml.save(request, filePath, formDTO, managerDTO, ceoDTO);

                break;
            case "1":
                // 회의록
                filePath = "src/main/resources/writeForms/meetingForm.html";
                successResult = loadHtml.save(request, filePath, formDTO, managerDTO, ceoDTO);

                break;
            case "2":
                // 협조문
                filePath = "src/main/resources/writeForms/cooperationForm.html";
                successResult = loadHtml.save(request, filePath, formDTO, managerDTO, ceoDTO);

                break;
        }

        String digitalApprovalName = successResult.get("title");
        DigitalApprovalDTO digitalApprovalDTO = new DigitalApprovalDTO();

        // 전자 결재 테이블 data insert
        digitalApprovalDTO = approvalService.approvalRequest(empId, digitalApprovalName, empDTO);
        Integer digitalApprovalId = digitalApprovalDTO.getDigitalApprovalId();

        // html 파일 PDF 저장
        loadHtml.htmlToPdf(filePath, fontPath, request);

        // sign PDF 가 저장되는 경로
        String outputPdfPath = "src/main/resources/approvalWaiting/signed" + digitalApprovalDTO.getDigitalApprovalId() + ".pdf";

        // PDF 파일 Sign 저장
        LoadHtml.addSignToPDF(pdfFilePath, imagePath, outputPdfPath, "drafter");

        // 전자 결재 테이블 data update (pdf 저장 경로)
        approvalService.updatePath(digitalApprovalId, outputPdfPath);

        return ResponseEntity.ok("HTML content received and processed successfully");
    }

    @Override
    @GetMapping("/pdf/{digitalApprovalId}/{projectName}")
    public ResponseEntity<Resource> getPdf(@PathVariable String projectName, @PathVariable Integer digitalApprovalId) {
        System.out.println("digitalApprovalId" + digitalApprovalId);

        try {
            // 프로젝트 이름을 기반으로 PDF 파일 경로 설정 (이 예시에서는 고정된 경로 사용)
            Path pdfPath = Paths
                    .get("src/main/resources/approvalWaiting/signed" + digitalApprovalId.toString() + ".pdf");
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
    public ResponseEntity<List<DigitalApprovalDTO>> getApprovalWaitingList() {
        List<DigitalApprovalDTO> digitalApprovalDTOList = approvalService.getApprovalWaitingList();
        return ResponseEntity.ok(digitalApprovalDTOList);
    }

    @Override
    @PostMapping("/requestpermission")
    public ResponseEntity<String> approvalRequestPermission(Map<String, String> request) throws IOException, DocumentException {

        Integer empId = Integer.parseInt(request.get("empId"));
        EmpDTO empDTO = empService.getEmpById(empId);

        Integer digitalApprovalId = Integer.parseInt(request.get("digitalApprovalId"));
        System.out.println("==========test start==========");

        // 문서 id를 통해서 객체 반환 받고 기안자 id, status 가져오기
        // 문서 id를 통해서 객체 반환 받고 status 값 가져오기
        DigitalApprovalDTO digitalApprovalDTO = approvalService.getDrafterId(digitalApprovalId);
        System.out.println("drafterId : " + digitalApprovalDTO.getDrafterId());
        System.out.println("isManagerStatus : " + digitalApprovalDTO.isManagerStatus());
        System.out.println("isCeoStatus : " + digitalApprovalDTO.isCeoStatus());

        Integer drafterId = digitalApprovalDTO.getDrafterId();
        boolean managerStatus = digitalApprovalDTO.isManagerStatus();
        boolean ceoStatus = digitalApprovalDTO.isCeoStatus();

        // 문서 번호의 기안자와 사용자가 다를 경우 (사원이 아닐 경우 -> 부장 , ceo)
        // 현재 empId : 2 , drafterId : 1
        if(!empId.equals(digitalApprovalId)){

            //부장 status false : 부장 문서 결재 대기함에 보이게 함
            if(!managerStatus){
                // 부장 사인 넣어주고 pdf로 저장
                // pdfFilePath : 사인되어있는 pdf 파일, imagePath : 본인 sign , outputPdfPath : 사인저장할 pdf
                String pdfFilePath = "src/main/resources/approvalWaiting/signed"+ digitalApprovalId+".pdf";
                String imagePath = empDTO.getEmpSign();
                String outputPdfPath = "src/main/resources/approvalWaiting/signed" + digitalApprovalId + ".pdf";
                LoadHtml.addSignToPDF(pdfFilePath,imagePath,outputPdfPath, "manager");

                // DB manager_status update
                approvalService.updateStatus(digitalApprovalId, "manager");


            } else { // 부장 status true : ceo 사인 넣어주기
                String pdfFilePath = "src/main/resources/approvalWaiting/signed"+ digitalApprovalId+".pdf";
                String imagePath = empDTO.getEmpSign();
                String outputPdfPath = "src/main/resources/approvalWaiting/signed" + digitalApprovalId + ".pdf";
                LoadHtml.addSignToPDF(pdfFilePath,imagePath,outputPdfPath, "ceo");
                // 내문서함으로 전송 (기안자 id, 문서경로)

                // DB ceo_status update
                approvalService.updateStatus(digitalApprovalId, "ceo");
            }

        } else { // 기안자와 사용자가 같을 경우
            // logic 처리할 필요가 없음
        }

        System.out.println("==========test end==========");

        /*
        EmpDTO empDTO = empService.getEmpById(empId);

        DigitalApprovalDTO approvalDTO = new DigitalApprovalDTO();
        Integer draftId = approvalDTO.getDrafterId();


        System.out.println("===========" + draftId + "=========");
        String imagePath = empDTO.getEmpSign();

        LocalDateTime digitalApprovalAt = LocalDateTime.now();

        String pdfFilePath = "src/main/resources/approvalWaiting/signed_" + empId + ".pdf";

        String outputPdfPath = "src/main/resources/approvalWaiting/permissionsigned_" + empId + ".pdf";
        // PDF 파일 Sign 저장
        // addSignToPDF(pdfFilePath, imagePath, outputPdfPath);
         */
        return ResponseEntity.ok("requestpermission : HTML content received and processed successfully");
    }

}
