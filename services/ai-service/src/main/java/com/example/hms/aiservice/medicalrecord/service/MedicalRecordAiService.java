package com.example.hms.aiservice.medicalrecord.service;

import com.example.hms.aiservice.medicalrecord.dto.DiagnosesInfoDTO;
import com.example.hms.aiservice.medicalrecord.dto.TreatmentSuggestionDTO;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "medicalRecordAiChatModel"
)
public interface MedicalRecordAiService {

    @SystemMessage("""
        You are an AI medical assistant providing treatment suggestions based on symptoms and diagnoses.
        Provide treatments and notes that are relevant to the symptoms and diagnoses provided.
        Ensure to structure your response in JSON format with exactly two fields: "treatments" and "notes".
        Keep your treatments under 300 characters and notes under 200 characters.
        Always provide evidence-based medical recommendations.
        Be concise, clear, and professional.
        """)
    TreatmentSuggestionDTO suggestTreatment(
            @UserMessage("""
                    Based on the following symptoms and diagnoses, suggest appropriate treatments:
                    
                    Symptoms: {{symptoms}}
                    Diagnoses: {{diagnoses}}
                    
                    """)
            DiagnosesInfoDTO diagnosesInfo
    );
}
