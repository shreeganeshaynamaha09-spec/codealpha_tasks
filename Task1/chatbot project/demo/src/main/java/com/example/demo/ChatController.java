// src/main/java/com/example/demo/ChatController.java

package com.example.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Define request and response records directly in this file for simplicity
record ChatRequest(String message) {}
record ChatResponse(String reply) {}

@RestController
public class ChatController {

    private final GeminiAiService geminiAiService;

    // Spring Boot automatically provides the ChatbotService instance here
    public ChatController(GeminiAiService geminiAiService) {
        this.geminiAiService = geminiAiService;;
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        // Call the service to get the actual bot reply
        String botReply = geminiAiService.getResponse(request.message());
        return new ChatResponse(botReply);
    }
}