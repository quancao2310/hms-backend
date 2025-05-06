package com.example.hms.aiservice.patientfacingcommunication.configuration;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PatientFacingCommunicationConfiguration {

    @Value("${GOOGLE_AI_GEMINI_API_KEY}")
    private String googleAiGeminiApiKey;

    @Value("${GOOGLE_AI_GEMINI_MODEL_NAME:gemini-2.0-flash}")
    private String googleAiGeminiModelName;

    private final PatientFacingCommunicationMemoryStore patientFacingCommunicationMemoryStore;

    @Bean
    ChatModel patientFacingCommunicationAiChatModel() {
        return dev.langchain4j.model.googleai.GoogleAiGeminiChatModel.builder()
                .apiKey(googleAiGeminiApiKey)
                .modelName(googleAiGeminiModelName)
                .temperature(1.0)
                .topP(0.95)
                .topK(256)
                .timeout(java.time.Duration.ofSeconds(60))
                .logRequestsAndResponses(true)
                .build();
    }

    @Bean
    ChatMemoryProvider patientFacingCommunicationAiChatMemoryProvider() {
        return patientId -> MessageWindowChatMemory.builder()
                .id(patientId)
                .maxMessages(25)
                .chatMemoryStore(patientFacingCommunicationMemoryStore)
                .build();
    }
}
