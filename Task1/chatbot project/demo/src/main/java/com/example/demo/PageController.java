package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.nio.charset.StandardCharsets;

@Controller
public class PageController {

    // Spring will still load the index.html file as a resource
    @Value("classpath:static/index.html")
    private Resource indexHtml;

    /**
     * This method handles requests for the homepage ("/").
     * It now MANUALLY reads the file's content into a String.
     * @ResponseBody ensures this String is sent as the direct response.
     * This is the most explicit and foolproof way to serve the page.
     */
    @GetMapping(value = "/", produces = "text/html") // Explicitly set the content type
    @ResponseBody
    public String getIndexHtmlAsString() {
        try {
            // Read the file's bytes and convert them to a UTF-8 String
            return new String(indexHtml.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return "<h1>Error loading page</h1><p>Could not load index.html. Please check server logs.</p>";
        }
    }
}