package com.ls.in.approval.controller.impl;

import com.lowagie.text.DocumentException;
import com.ls.in.approval.controller.DigitalApprovalController;
import com.ls.in.approval.dto.CeoDTO;
import com.ls.in.approval.dto.DigitalApprovalDTO;
import com.ls.in.approval.dto.FormDTO;
import com.ls.in.approval.dto.ManagerDTO;
import com.ls.in.approval.service.DigitalApprovalService;
import com.ls.in.approval.util.LoadHtml;

import com.ls.in.document.dto.DocumentInitDTO;
import com.ls.in.document.service.impl.DocumentServiceImpl;
import com.ls.in.global.emp.domain.dto.DepartmentDTO;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.service.EmpService;
import com.ls.in.global.emp.service.DepartmentService;
import com.ls.in.global.util.Utils;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/lighting_solutions/digital/approval")
@CrossOrigin(origins = "http://localhost:3000")
public class DigitalApprovalControllerImpl implements DigitalApprovalController {

    private final DigitalApprovalService approvalService;
    private final DocumentServiceImpl documentService;

    private final LoadHtml loadHtml = new LoadHtml();

    private final EmpService empService;
    private final DepartmentService departmentService;

    @Autowired
    public DigitalApprovalControllerImpl(DigitalApprovalService approvalService, EmpService empService, DocumentServiceImpl documentService, DepartmentService departmentService) {
        this.approvalService = approvalService;
        this.empService = empService;
        this.documentService = documentService;
        this.departmentService = departmentService;
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

        // 로그인한 사원 ID
        Integer empId = Integer.parseInt(request.get("empId"));

        // empId와 일치하는 사원 정보 가져오기 (직급, 부서)
        EmpDTO empDTO = empService.getEmpById(empId);

        // HTML 들어갈 정보 (FormDTO)
        FormDTO formDTO = new FormDTO();
        Integer departmentId = Utils.converterDepartment(empDTO.getDepartment().getDepartmentId());
        DepartmentDTO departmentDTO = departmentService.getDepartmentById(departmentId);
        formDTO.setDepartment(departmentDTO.getDepartmentName());
        formDTO.setPosition(empDTO.getPosition().getPositionName());
        formDTO.setName(empDTO.getEmpName());
        formDTO.setDigitalApprovalCreatedAt(LocalDateTime.now());

        // empId와 일치하는 사원의 부서의 특정 직급과 일치하는 사원 정보 (직급, 이름) - 부장
        //EmpDTO empDTO1 = empService.getEmpByIdAndDepartmentAndPosition(empId, 2);
        EmpDTO empDTO1 = empService.findByPositionIdAndDepartmentId(2, departmentId);

        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setName(empDTO1.getEmpName());
        managerDTO.setPosition(empDTO1.getPosition().getPositionName());
        //managerDTO.setDepartment(empDTO1.getPosition().getPositionName());

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
        loadHtml.htmlToPdf(filePath, fontPath, request, digitalApprovalId, formDTO);

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

        DigitalApprovalDTO digitalApprovalDTO = approvalService.getDrafterId(digitalApprovalId);
        boolean digitalApprovalType = digitalApprovalDTO.isDigitalApprovalType();

        try {
            // 프로젝트 이름을 기반으로 PDF 파일 경로 설정 (이 예시에서는 고정된 경로 사용)
            Path pdfPath;
            // 반려함
            if(digitalApprovalType){
                pdfPath = Paths
                        .get("src/main/resources/approvalReject/signed" + digitalApprovalId.toString() + ".pdf");
            } else {
                pdfPath = Paths
                        .get("src/main/resources/approvalWaiting/signed" + digitalApprovalId.toString() + ".pdf");
            }

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
    public ResponseEntity<List<DigitalApprovalDTO>> getApprovalWaitingList(@RequestParam Map<String, String> request) {

        Integer empId = Integer.parseInt(request.get("empId"));

        //empId를 통해 drafterID 와 해당 직급 가져오기
        EmpDTO empDTO = empService.getEmpById(empId);
        Integer position = empDTO.getPosition().getPositionId();
        Integer department = 0;

        /*
        if(!empId.equals(3)){
            department = empDTO.getDepartment().getDepartmentId();
        } else {
            department = null;
        }
         */
        if(!position.equals(1)){
            department = empDTO.getDepartment().getDepartmentId();
            System.out.println("department" + department);
        } else {
            department = null;
        }



        List<DigitalApprovalDTO> digitalApprovalDTOList = new ArrayList<>();

        // 대표 이사
        if(position.equals(1)){
            digitalApprovalDTOList = approvalService.getApprovalWaitingList();
        } else if(position.equals(2)){ // 부장
            digitalApprovalDTOList = approvalService.getApprovalWaitingListByManager(department);
        } else { // 부장 밑 사원
            digitalApprovalDTOList = approvalService.getApprovalWaitingListByEmployee(empId);
        }

        System.out.println(digitalApprovalDTOList);

        return ResponseEntity.ok(digitalApprovalDTOList);
    }

    @Override
    @PostMapping("/requestpermission")
    public ResponseEntity<String> approvalRequestPermission(Map<String, String> request) throws IOException, DocumentException {

        Integer empId = Integer.parseInt(request.get("empId"));
        EmpDTO empDTO = empService.getEmpById(empId);

        Integer digitalApprovalId = Integer.parseInt(request.get("digitalApprovalId"));

        // 문서 id를 통해서 객체 반환 받고 기안자 id, status 가져오기
        // 문서 id를 통해서 객체 반환 받고 status 값 가져오기
        DigitalApprovalDTO digitalApprovalDTO = approvalService.getDrafterId(digitalApprovalId);

        Integer drafterId = digitalApprovalDTO.getDrafterId();
        boolean managerStatus = digitalApprovalDTO.isManagerStatus();

        // 문서 번호의 기안자와 사용자가 다를 경우 (사원이 아닐 경우 -> 부장 , ceo)
        if(!empId.equals(drafterId)){
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
                String outputPdfPath = "src/main/resources/approvalComplete/signed" + digitalApprovalId + ".pdf";
                LoadHtml.addSignToPDF(pdfFilePath,imagePath,outputPdfPath, "ceo");

                Path approvalWaiting = Paths.get(pdfFilePath);
                Files.delete(approvalWaiting);


                // 내문서함으로 전송 (기안자 id, 문서경로)
                /* 꼭 해야함 -> 꼭 했음돵 ㅎㅎ */
                String fileName = "signed" + digitalApprovalId + ".pdf";
                DocumentInitDTO documentInitDTO = new DocumentInitDTO("전자결재문서" + digitalApprovalId,
                        "전자결재내용" + digitalApprovalId,
                        fileName,
                        "approval",
                        drafterId);
                documentService.saveDocument(documentInitDTO);
                // DB ceo_status update
                approvalService.updateStatus(digitalApprovalId, "ceo");
            }
        } else { // 기안자와 사용자가 같을 경우
            // logic 처리할 필요가 없음
        }

        return ResponseEntity.ok("requestpermission : HTML content received and processed successfully");
    }

    @Override
    @PostMapping("/requestreject")
    public ResponseEntity<String> approvalRequestReject(@RequestBody Map<String, String> request) throws IOException, DocumentException {
        Integer empId = Integer.parseInt(request.get("empId"));
        Integer digitalApprovalId = Integer.parseInt(request.get("digitalApprovalId"));

        DigitalApprovalDTO digitalApprovalDTO = approvalService.getDrafterId(digitalApprovalId);

        boolean managerStatus = digitalApprovalDTO.isManagerStatus();
        boolean ceoStatus = digitalApprovalDTO.isCeoStatus();

        // 문서가 이미 승인된 상태인지 확인
        if (!managerStatus || !ceoStatus) {
            // 반려 상태로 업데이트
            approvalService.updateRejectionStatus(digitalApprovalId, managerStatus, ceoStatus);

            // 결재 대기 문서함 경로
            String savedPdfPath = "src/main/resources/approvalWaiting/signed" + digitalApprovalDTO.getDigitalApprovalId() + ".pdf";
            // 결재 반려 문서함 경로
            String outputPdfPath = "src/main/resources/approvalReject/signed" + digitalApprovalDTO.getDigitalApprovalId() + ".pdf";

            Path savedPath = Paths.get(savedPdfPath);
            Path outputPath = Paths.get(outputPdfPath);

            // 문서 이동
            Files.move(savedPath, outputPath);

            // 문서 지우기
            Files.delete(savedPath);

            // 전자 결재 테이블 data update (pdf 저장 경로)
            approvalService.updatePath(digitalApprovalId, outputPdfPath);

            return ResponseEntity.ok("문서가 반려 대기함으로 이동되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("이미 승인된 문서는 반려할 수 없습니다.");
        }
    }


    // sercurity 서버로 보낼 pdf 파일
    /*
    @PostMapping("/sendPdfToSecurity/{digitalApprovalId}")
    public ResponseEntity<String> sendPdfToSecurity(@PathVariable Integer digitalApprovalId) {
        DigitalApprovalDTO digitalApprovalDTO = approvalService.getDrafterId(digitalApprovalId);
        boolean digitalApprovalType = digitalApprovalDTO.isDigitalApprovalType();

        Path pdfPath;
        if(digitalApprovalType){
            pdfPath = Paths.get("src/main/resources/approvalReject/signed" + digitalApprovalId + ".pdf");
        } else {
            pdfPath = Paths.get("src/main/resources/approvalWaiting/signed" + digitalApprovalId + ".pdf");
        }

        if (!Files.exists(pdfPath)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PDF not found");
        }

        try {
            byte[] pdfBytes = Files.readAllBytes(pdfPath);
            String securityServerUrl = "http://security-server-url/api/v1/receivePdf";

            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", pdfPath.getFileName().toString(),
                            RequestBody.create(MediaType.parse("application/pdf"), pdfBytes))
                    .build();

            Request request = new Request.Builder()
                    .url(securityServerUrl)
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            return ResponseEntity.ok("PDF sent to security server successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending PDF to security server");
        }
    }

     */

}
