package com.ls.in.approval.controller;

import com.lowagie.text.DocumentException;

import com.ls.in.approval.dto.DigitalApprovalDTO;
import com.ls.in.calendar.dto.ParticipantDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DigitalApprovalController {

    /**
     * @apiNote resources에 저장되어 있는 html 폼 가져오기
     * @param status
     * @return
     */
    ResponseEntity<String> getHtmlContent(@RequestParam Integer status);

    /**
     * @apiNote 사용자가 작성한 전재결재 저장하고, pdf 변환하고 해당 사용자의 sign pdf에 저장
     * @param request
     * @return
     */
    ResponseEntity<String> approvalRequest(@RequestBody Map<String, String> request)
            throws IOException, DocumentException;

    /**
     * @apiNote PDF Viewer Api 를 활용해서 PDF 화면 띄우기
     * @param projectName
     * @return
     */
    ResponseEntity<Resource> getPdf(@PathVariable String projectName, @PathVariable Integer digitalApprovalId);

    /**
     * @apiNote digitalApprovalId 를 조회해서 결재 대기 문서 조회
     * @param request
     * @return
     */
    ResponseEntity<List<DigitalApprovalDTO>> getApprovalWaitingList(@RequestParam Map<String, String> request);

    /**
     * @apiNote empId를 조회해서 sign PDF에 서명 및 날짜 추가
     * @param request
     * @return
     */
    ResponseEntity<String> approvalRequestPermission(@RequestBody Map<String, String> request) throws IOException, DocumentException;

    /**
     * @apiNote empId를 조회해서 반려 버튼 눌렀을 때 결재 반려 문서함으로 이동
     * @param request
     * @return
     */
    ResponseEntity<String> approvalRequestReject(@RequestBody Map<String, String> request) throws IOException, DocumentException;
}






