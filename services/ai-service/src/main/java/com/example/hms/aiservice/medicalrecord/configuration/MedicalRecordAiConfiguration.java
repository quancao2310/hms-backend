package com.example.hms.aiservice.medicalrecord.configuration;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class MedicalRecordAiConfiguration {

    @Value("${GOOGLE_AI_GEMINI_API_KEY}")
    private String googleAiGeminiApiKey;

    @Value("${GOOGLE_AI_GEMINI_MODEL_NAME:gemini-2.0-flash}")
    private String googleAiGeminiModelName;

    @Bean
    ChatModel medicalRecordAiChatModel() {
        return GoogleAiGeminiChatModel.builder()
                .apiKey(googleAiGeminiApiKey)
                .modelName(googleAiGeminiModelName)
                .temperature(0.2)
                .topP(0.8)
                .topK(64)
                .timeout(Duration.ofSeconds(60))
                .logRequestsAndResponses(true)
                .build();
    }
}
