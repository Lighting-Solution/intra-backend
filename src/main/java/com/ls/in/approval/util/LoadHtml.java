package com.ls.in.approval.util;

import com.itextpdf.text.pdf.BaseFont;
import com.lowagie.text.DocumentException;
import com.ls.in.approval.dto.DigitalApprovalDTO;
import com.ls.in.approval.dto.FormDTO;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import lombok.NoArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
public class LoadHtml {

    public String load(String filePath) {
        String htmlContent = "";

        try {
            File file = new File(getClass().getClassLoader().getResource(filePath).getFile());
            String html = new String(Files.readAllBytes(Paths.get(file.getPath())));

            // HTML -> Jsoup 변경 (xmlParser 이용)
            Document document = Jsoup.parse(html, "", org.jsoup.parser.Parser.xmlParser());

            // html body 내용 가져오기
            Element body = document.body();
            htmlContent = body.html();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlContent;
    }

    public Map<String, String> save(Map<String, String> request, String filePath, FormDTO formDTO) {


        String encodedHtmlContent = request.get("html");

        String htmlContent;
        String title = "";

        String name = formDTO.getName();
        String department = formDTO.getDepartment();
        LocalDateTime digitalApprovalCreatedAt = formDTO.getDigitalApprovalCreatedAt();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = digitalApprovalCreatedAt.format(formatter);
      
        System.out.println("name :" + name);
        System.out.println("department :" + department);


        try {
            htmlContent = URLDecoder.decode(encodedHtmlContent, StandardCharsets.UTF_8.name());

            // Jsoup을 사용하여 HTML 파싱
            Document document = Jsoup.parse(htmlContent);

            // ID가 "title"인 input 요소 찾기
            Element inputElement = document.getElementById("title");
            if (inputElement != null) {
                String inputValue = inputElement.attr("value");
                title = inputValue;
            } else {
                System.err.println("Input element with id 'title' not found.");
            }

            // ID가 "digitalApprovalEmpName"인 요소 찾기

            this.changeValue(document, "digitalApprovalEmpName", "div", name);

            // ID가 "digitalApprovalDepartment"인 요소 찾기
            this.changeValue(document, "digitalApprovalDepartment", "div", department);

            // ID가 "digitalApprovalCreatedAt" 인 요소 찾기
            this.changeValue(document, "digitalApprovalCreatedAt", "div", formattedDate);

            // ID가 approvalDepartment1 인 요소 찾기
            this.changeValue(document, "approvalDepartment1", "span", "솔루션 개발부");

            // ID가 approvalName1 인 요소 찾기
            this.changeValue(document, "approvalName1", "span", name);


            // ID가 approvalDate1 인 요소 찾기
            this.changeValue(document, "approvalDate1", "span", formattedDate);


            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            document.outputSettings().escapeMode(Entities.EscapeMode.xhtml);


            // System.out.println(htmlContent);
            htmlContent = document.html();

            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            document.outputSettings().escapeMode(Entities.EscapeMode.xhtml);

            //System.out.println(htmlContent);
            htmlContent = document.html();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Map.of("status", "error", "message", "Failed to decode HTML.");
        }

        try {
            Files.write(Paths.get(filePath), htmlContent.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
            Map<String, String> successResult = new HashMap<>();
            successResult.put("status", "success");
            successResult.put("message", "HTML saved successfully.");
            successResult.put("title", title);
            return successResult;
        } catch (IOException e) {
            e.printStackTrace();
            return Map.of("status", "error", "message", "Failed to save HTML.");
        }

    }


    private Document changeValue(Document document, String elementId, String type, String value) {
        Element inputElement = document.getElementById(elementId);
        if (inputElement != null) {
            if (type.equals("div") || type.equals("span")) {
                inputElement.text(value);
            }
        } else {
            System.err.println(elementId + "not found.");
        }
        return document;
    }

    public void htmlToPdf(String filePath, String fontPath, Map<String, String> request)
            throws IOException, DocumentException {

        // html -> pdf 변경하기 (결재진행중 저장)

        // Ensure HTML content is read as UTF-8
        byte[] htmlContentBytes = Files.readAllBytes(Paths.get(filePath));
        String htmlContent = new String(htmlContentBytes, StandardCharsets.UTF_8);

        // Inject request map values into HTML content
        for (Map.Entry<String, String> entry : request.entrySet()) {
            String placeholder = String.format("{{%s}}", entry.getKey());
            htmlContent = htmlContent.replace(placeholder, entry.getValue());
        }

        // Convert input fields and text areas with values
        htmlContent = htmlContent.replaceAll("<input type=\"text\" placeholder=\"[^\"]*\" value=\"([^\"]*)\" />", "$1");
        htmlContent = htmlContent.replaceAll("<input[^>]*id=\"([^\"]*)\"[^>]*value=\"([^\"]*)\"[^>]*>", "$2");

        // Handle <textarea> elements specifically
        Pattern pattern = Pattern.compile("(?s)<textarea[^>]*>(.*?)</textarea>");
        Matcher matcher = pattern.matcher(htmlContent);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String textareaContent = matcher.group(1);
            textareaContent = textareaContent.replace("\n", "<br />");
            matcher.appendReplacement(sb, "<div>" + textareaContent + "</div>");
        }
        matcher.appendTail(sb);
        htmlContent = sb.toString();
        System.out.println("--------------------------------------------------------");
        //System.out.println(htmlContent);  html 코드 보여주기

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
    }

    // 전자 결재 서명
    public static void addSignToPDF(String pdfFilePath, String imagePath, String outputPdfPath) throws IOException {
        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
            PDPage page = document.getPage(0); // Add signature to the first page

            PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, document);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page,
                    PDPageContentStream.AppendMode.APPEND, true, true)) {
                // 1번째 사인 위치
                contentStream.drawImage(pdImage, 470, 795, 40, 35);

                // 2번째 사인 위치
                // contentStream.drawImage(pdImage, 550, 795, 40, 35);

                // 3번째 사인 위치
                // contentStream.drawImage(pdImage, 630, 795, 40, 35);
            }

            // Save the signed PDF to the output path
            try (OutputStream os = new FileOutputStream(outputPdfPath)) {
                document.save(os);
            }
        }
    }
}
