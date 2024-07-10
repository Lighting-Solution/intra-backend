package com.ls.in.approval.util;

import com.itextpdf.text.pdf.BaseFont;
import com.lowagie.text.DocumentException;
import com.ls.in.approval.dto.CeoDTO;
import com.ls.in.approval.dto.FormDTO;
import com.ls.in.approval.dto.ManagerDTO;
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

    public Map<String, String> save(Map<String, String> request, String filePath, FormDTO formDTO,
                                    ManagerDTO managerDTO, CeoDTO ceoDTO) {

        String encodedHtmlContent = request.get("html");


        String htmlContent;
        String title = "";

        // 기안자 결재 정보
        String drafterName = formDTO.getName();
        String drafterDepartment = formDTO.getDepartment();
        String drafterPosition = formDTO.getPosition();

        LocalDateTime digitalApprovalCreatedAt = formDTO.getDigitalApprovalCreatedAt();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = digitalApprovalCreatedAt.format(formatter);

        // 부장 결재 정보
        String managerName = managerDTO.getName();
        String managerPosition = managerDTO.getPosition();

        // ceo 결재 정보
        String ceoName = ceoDTO.getName();
        String ceoPosition = ceoDTO.getPosition();

        try {
            htmlContent = URLDecoder.decode(encodedHtmlContent, StandardCharsets.UTF_8.name());
            System.out.println(htmlContent);

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
            this.changeValue(document, "digitalApprovalEmpName", "div", drafterName);

            // ID가 "digitalApprovalDepartment"인 요소 찾기
            this.changeValue(document, "digitalApprovalDepartment", "div", drafterDepartment);

            // ID가 "digitalApprovalCreatedAt" 인 요소 찾기
            this.changeValue(document, "digitalApprovalCreatedAt", "div", formattedDate);

            // ID가 approvalPosition1 인 요소 찾기
            this.changeValue(document, "approvalPosition1", "span", drafterPosition);

            // ID가 approvalName1 인 요소 찾기
            this.changeValue(document, "approvalName1", "span", drafterName);

            // ID가 approvalPosition2 인 요소 찾기
            this.changeValue(document, "approvalPosition2", "span", managerPosition);

            // ID가 approvalName2 인 요소 찾기
            this.changeValue(document, "approvalName2", "span", managerName);

            // ID가 approvalPosition3 인 요소 찾기
            this.changeValue(document, "approvalPosition3", "span", ceoPosition);

            // ID가 approvalName3 인 요소 찾기
            this.changeValue(document, "approvalName3", "span", ceoName);

            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            document.outputSettings().escapeMode(Entities.EscapeMode.xhtml);

            htmlContent = document.html();

            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            document.outputSettings().escapeMode(Entities.EscapeMode.xhtml);

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

    public void htmlToPdf(String filePath, String fontPath, Map<String, String> request, Integer digitalApprovalId, FormDTO formDTO)
            throws IOException, DocumentException {
        // 문서번호 가져오기
        String approvalId = String.valueOf(digitalApprovalId);

        // 부서명, 기안일 가져오기
        String departmentName = formDTO.getDepartment();
        LocalDateTime digitalApprovalCreatedAt = formDTO.getDigitalApprovalCreatedAt();
        String formatterPattern = "yyyyMMdd";  // formatter 객체 대신 문자열로 변경
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatterPattern);

        // 날짜를 원하는 형식으로 포맷팅하는 예시
        String formattedDate = digitalApprovalCreatedAt.format(formatter);

        // html -> pdf 변경하기 (결재진행중 저장)

        // Ensure HTML content is read as UTF-8
        byte[] htmlContentBytes = Files.readAllBytes(Paths.get(filePath));
        String htmlContent = new String(htmlContentBytes, StandardCharsets.UTF_8);


        for (Map.Entry<String, String> entry : request.entrySet()) {
            String placeholder = String.format("{{%s}}", entry.getKey());
            htmlContent = htmlContent.replace(placeholder, entry.getValue());
        }

        // html 안에 요소 변경하기
        htmlContent = htmlContent.replaceAll("<input type=\"text\" placeholder=\"[^\"]*\" value=\"([^\"]*)\" />", "$1");
        htmlContent = htmlContent.replaceAll("<input[^>]*id=\"([^\"]*)\"[^>]*value=\"([^\"]*)\"[^>]*>", "$2");
        htmlContent = htmlContent.replaceAll("<div id=\"digitalApprovalId\"></div>", "<div id=\"digitalApprovalId\">" +departmentName+"-"+formattedDate+"-"+approvalId + "</div>");


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

        // html 을 pdf 로 쓰기위해 ITextRenderer 사용
        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver fontResolver = renderer.getFontResolver();
        fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED); // 유니코드 폰트 설정

        renderer.setDocumentFromString(htmlContent);
        renderer.layout();

        // 결재 대기 문서 폴더가 없으면 생성
        Path approvalWaitingDir = Paths.get("src/main/resources/ApprovalWaiting");
        if (!Files.exists(approvalWaitingDir)) {
            Files.createDirectories(approvalWaitingDir);
        }

        // 결재 대기 문서 폴더에 pdf 저장
        Path pdfPath = approvalWaitingDir.resolve("saved_approval.pdf");
        try (OutputStream os = Files.newOutputStream(pdfPath)) {
            renderer.createPDF(os);
        }

        // PDF 를 가져와서 첫 화면을 이미지로 바꾸는 작업
        BufferedImage image;
        try (PDDocument document = PDDocument.load(pdfPath.toFile())) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            image = pdfRenderer.renderImageWithDPI(0, 300); // Render at 300 DPI

            // 문서 닫기
            document.close();
        }

        // 이미지를 png 파일을 결재 대기 문서함에 저장
        Path pngPath = approvalWaitingDir.resolve("saved_approval.png");
        ImageIO.write(image, "png", pngPath.toFile());

        // 사인 이미지를 추가해 새로운 PDF 생성
        try (PDDocument pdfDocument = new PDDocument()) {
            PDPage page = new PDPage();
            pdfDocument.addPage(page);
            PDImageXObject pdImage = PDImageXObject.createFromFile(pngPath.toString(), pdfDocument);
            try (PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page)) {
                contentStream.drawImage(pdImage, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
            }

            Path finalPdfPath = approvalWaitingDir.resolve("final_saved_approval.pdf");
            try (OutputStream outputStream = Files.newOutputStream(finalPdfPath)) {
                pdfDocument.save(outputStream);
            }

            Files.deleteIfExists(pngPath);
        }
    }

    public static void addSignToPDF(String pdfFilePath, String imagePath, String outputPdfPath, String signType) throws IOException {
        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
            // 첫 화면 지정하기
            PDPage page = document.getPage(0);
            PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, document);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page,
                    PDPageContentStream.AppendMode.APPEND, true, true)) {

                switch (signType){
                    case "drafter" :
                        // 1번째 사인 위치
                        contentStream.drawImage(pdImage, 470, 795, 40, 35);
                        break;
                    case "manager":
                        // 2번째 사인 위치
                        contentStream.drawImage(pdImage, 550, 795, 40, 35);
                        break;
                    case "ceo":
                        // 3번째 사인 위치
                        contentStream.drawImage(pdImage, 630, 795, 40, 35);
                        break;
                }
            }

            try (OutputStream os = new FileOutputStream(outputPdfPath)) {
                document.save(os);
            }
        }
    }
}
