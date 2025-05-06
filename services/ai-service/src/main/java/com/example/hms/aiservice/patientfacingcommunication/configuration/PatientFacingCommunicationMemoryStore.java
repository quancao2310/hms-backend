package com.example.hms.aiservice.patientfacingcommunication.configuration;

import com.example.hms.aiservice.patientfacingcommunication.repository.ChatMemoryPersistenceRepository;
import com.example.hms.models.internal.ai.ChatMemoryPersistence;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PatientFacingCommunicationMemoryStore implements ChatMemoryStore {

    private final ChatMemoryPersistenceRepository chatMemoryPersistenceRepository;

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        UUID patientId = (UUID) memoryId;
        Optional<ChatMemoryPersistence> existingMemory =
                chatMemoryPersistenceRepository.findByPatientId(patientId);

        ChatMemoryPersistence chatMemoryPersistence = existingMemory.orElseGet(() -> ChatMemoryPersistence.builder()
                .patientId(patientId)
                .build());

        chatMemoryPersistence.setMessages(ChatMessageSerializer.messagesToJson(messages));
        chatMemoryPersistenceRepository.save(chatMemoryPersistence);
    }

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        UUID patientId = (UUID) memoryId;
        Optional<ChatMemoryPersistence> existingMemory =
                chatMemoryPersistenceRepository.findByPatientId(patientId);

        return existingMemory.map(chatMemoryPersistence ->
                ChatMessageDeserializer.messagesFromJson(chatMemoryPersistence.getMessages()))
                .orElse(List.of());
    }

    @Override
    public void deleteMessages(Object memoryId) {
        UUID patientId = (UUID) memoryId;
        chatMemoryPersistenceRepository.findByPatientId(patientId)
                .ifPresent(chatMemoryPersistenceRepository::delete);
    }
}
