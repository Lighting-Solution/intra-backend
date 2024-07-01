package com.ls.in.approval.controller.impl;

import com.itextpdf.text.pdf.BaseFont;
import com.lowagie.text.DocumentException;
import com.ls.in.approval.controller.DigitalApprovalController;
import com.ls.in.approval.service.DigitalApprovalService;
import com.ls.in.approval.util.LoadHtml;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            default:
                break;
        }

        return ResponseEntity.ok(htmlContent);
    }

    // 결재 요청
    /*
    @PostMapping("/approval/request")
    public ResponseEntity<String> approvalRequest(@RequestBody Map<String, String> request) {
        String status = request.get("status");
        String filePath = "";
        System.out.println(status);

        // 서버에 작성한 전자결재 저장하기
        switch (status) {
            case "0":
                // 기안문
                filePath = "src/main/resources/writeForms/draftForm.html";
                loadHtml.save(request,filePath);
            case "1":
                // 회의록
                filePath = "src/main/resources/writeForms/meetingForm.html";
                loadHtml.save(request,filePath);
            case "2":
                // 협조문
                filePath = "src/main/resources/writeForms/cooperationForm.html";
                loadHtml.save(request,filePath);
        }

        return ResponseEntity.ok("HTML content received and processed successfully");
    }
    */


    // pdf 신형
    @PostMapping("/approval/request")
    public ResponseEntity<String> approvalRequest(@RequestBody Map<String, String> request) throws IOException, IOException, DocumentException {
        String status = request.get("status");
        String filePath = "";
        System.out.println(status);

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
            default:
                break;
        }

        // html -> pdf 변경하기 (결재진행중 저장)
        String fontPath = "src/main/resources/fonts/NotoSansKR-Regular.ttf";

        // Ensure HTML content is read as UTF-8
        byte[] htmlContentBytes = Files.readAllBytes(Paths.get(filePath));
        String htmlContent = new String(htmlContentBytes, StandardCharsets.UTF_8);

        // Convert HTML to PDF using ITextRenderer
        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver fontResolver = renderer.getFontResolver();
        fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED); // 유니코드 폰트 설정
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();

        // Create approvalWaiting directory if it doesn't exist
        Path approvalWaitingDir = Paths.get("src/main/resources/ApprovalWaiting");
        if (!Files.exists(approvalWaitingDir)) {
            Files.createDirectories(approvalWaitingDir);
        }

        // Save PDF to the approvalWaiting directory
        Path pdfPath = approvalWaitingDir.resolve("saved_approval.pdf");
        try (OutputStream os = Files.newOutputStream(pdfPath)) {
            renderer.createPDF(os);
        }

        // Load the PDF and convert the first page to an image
        BufferedImage image;
        try (PDDocument document = PDDocument.load(pdfPath.toFile())) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            image = pdfRenderer.renderImageWithDPI(0, 300); // Render at 300 DPI

            // Clean up
            document.close();
        }

        // Save the image to a PNG file in the approvalWaiting directory
        Path pngPath = approvalWaitingDir.resolve("saved_approval.png");
        ImageIO.write(image, "png", pngPath.toFile());

        // Create a new PDF document with the image
        try (PDDocument pdfDocument = new PDDocument()) {
            PDPage page = new PDPage();
            pdfDocument.addPage(page);
            PDImageXObject pdImage = PDImageXObject.createFromFile(pngPath.toString(), pdfDocument);
            try (PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page)) {
                contentStream.drawImage(pdImage, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
            }

            // Save the new PDF to the approvalWaiting directory
            Path finalPdfPath = approvalWaitingDir.resolve("final_saved_approval.pdf");
            try (OutputStream outputStream = Files.newOutputStream(finalPdfPath)) {
                pdfDocument.save(outputStream);
            }

            // Clean up
            Files.deleteIfExists(pngPath);
        }

        return ResponseEntity.ok("HTML content received and processed successfully");
    }



}
