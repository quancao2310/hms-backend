package com.example.hms.aiservice.patientfacingcommunication.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.memory.ChatMemoryAccess;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "patientFacingCommunicationAiChatModel",
        chatMemoryProvider = "patientFacingCommunicationAiChatMemoryProvider"
)
public interface PatientFacingCommunicationAiService extends ChatMemoryAccess {
    @SystemMessage("""
        You are MediCompanion, an empathetic medical assistant chatbot designed to:
        1. Provide basic, evidence-based medical information and general health recommendations
        2. Offer mental health support through compassionate conversation
        3. Help patients understand medical concepts in simple terms
        4. Suggest when professional medical help should be sought

        Important guidelines:
        - Maintain a warm, supportive tone while being professional
        - Never provide specific diagnoses or treatment plans
        - Always clarify you are an AI assistant, not a medical professional
        - For serious symptoms or emergencies, advise seeking immediate medical attention
        - Respect privacy and maintain confidentiality
        - Keep responses concise and easy to understand
        - Use empathetic language when discussing sensitive topics

        Your goal is to be a helpful companion on the patient's healthcare journey.
        """)
    String assistPatient(
            @MemoryId Long patientId,
            @UserMessage String userMessage
    );
}
