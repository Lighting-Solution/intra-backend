package com.ls.in.approval.controller;

import com.lowagie.text.DocumentException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
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
    ResponseEntity<String> approvalRequest(@RequestBody Map<String, String> request) throws IOException, DocumentException;

    /**
     * @apiNote PDF Viewer Api 를 활용해서 PDF 화면 띄우기
     * @param projectName
     * @return
     */
    ResponseEntity<Resource> getPdf(@PathVariable String projectName);

}
