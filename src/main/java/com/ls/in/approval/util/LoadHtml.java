package com.ls.in.approval.util;

import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;

@NoArgsConstructor
public class LoadHtml {

    public String load(String filePath) {
        String htmlContent = "";

        try {
            File file = new File(getClass().getClassLoader().getResource(filePath).getFile());
            String html = new String(Files.readAllBytes(Paths.get(file.getPath())));

            // Parse the HTML file using Jsoup with XML parser
            Document document = Jsoup.parse(html, "", org.jsoup.parser.Parser.xmlParser());

            // Extract the content inside the <body> tag
            Element body = document.body();
            htmlContent = body.html();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return htmlContent;
    }



    public Map<String, String> save(Map<String, String> request, String filePath){
        String encodedHtmlContent = request.get("html");
        String htmlContent;
        try {
            htmlContent = URLDecoder.decode(encodedHtmlContent, StandardCharsets.UTF_8.name());
            System.out.println(htmlContent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Map.of("status", "error", "message", "Failed to decode HTML.");
        }

        try {
            Files.write(Paths.get(filePath), htmlContent.getBytes(StandardCharsets.UTF_8), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
            return Map.of("status", "success", "message", "HTML saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return Map.of("status", "error", "message", "Failed to save HTML.");
        }

    }
}
