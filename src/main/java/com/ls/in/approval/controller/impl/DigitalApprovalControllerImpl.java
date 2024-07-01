package com.ls.in.approval.controller.impl;

import com.ls.in.approval.controller.DigitalApprovalController;
import com.ls.in.approval.service.DigitalApprovalService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/lighting_solutions")
@CrossOrigin(origins = "http://localhost:3000")
public class DigitalApprovalControllerImpl implements DigitalApprovalController {

    private DigitalApprovalService approvalService;

    @Autowired
    public DigitalApprovalControllerImpl(DigitalApprovalService approvalService){
        this.approvalService = approvalService;
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    // resources 에서 폼 가져오기
    @GetMapping("/getHtmlContent")
    public ResponseEntity<String> getHtmlContent() {
        String htmlContent = "";

        try {
            // Load the HTML file from the resources directory
            File file = new File(getClass().getClassLoader().getResource("forms/draftForm.html").getFile());
            String html = new String(Files.readAllBytes(Paths.get(file.getPath())));

            // Parse the HTML file using Jsoup
            Document document = Jsoup.parse(html);

            // Extract the content inside the <body> tag
            Element body = document.body();
            htmlContent = body.html();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(htmlContent);
    }
}
