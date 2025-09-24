// src/main/java/com/example/demo/GeminiAiService.java

package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GeminiAiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getResponse(String userInput) {
        // We use gemini-1.5-flash as it's fast and efficient for chat.
        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;

        // Create the request body according to the Gemini API specification
        GeminiPart part = new GeminiPart(userInput);
        GeminiContent content = new GeminiContent(List.of(part));
        GeminiRequest request = new GeminiRequest(List.of(content));

        try {
            GeminiResponse response = restTemplate.postForObject(apiUrl, request, GeminiResponse.class);

            // Extract the text from the nested response object
            if (response != null && !response.candidates().isEmpty()) {
                GeminiContent responseContent = response.candidates().get(0).content();
                if (responseContent != null && !responseContent.parts().isEmpty()) {
                    return responseContent.parts().get(0).text();
                }
            }
            return "Sorry, I couldn't get a response.";
        } catch (Exception e) {
            // Log the error for debugging
            e.printStackTrace();
            return "Sorry, an error occurred while connecting to the AI service.";
        }
    }
}

// Helper records to model the Gemini API's JSON structure
record GeminiRequest(List<GeminiContent> contents) {}
record GeminiContent(List<GeminiPart> parts) {}
record GeminiPart(String text) {}

record GeminiResponse(List<GeminiCandidate> candidates) {}
record GeminiCandidate(GeminiContent content) {}