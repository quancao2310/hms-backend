package com.example.hms.aiservice.internaldocument.configuration;

import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class InternalDocumentConfiguration {

    @Value("${GOOGLE_AI_GEMINI_API_KEY}")
    private String googleAiGeminiApiKey;

    @Value("${GOOGLE_AI_GEMINI_MODEL_NAME:gemini-2.0-flash}")
    private String googleAiGeminiModelName;

    @Value("${SPRING_DATASOURCE_HOST:localhost}")
    private String dataSourceHost;

    @Value("${SPRING_DATASOURCE_PORT:5432}")
    private int dataSourcePort;

    @Value("${SPRING_DATASOURCE_DATABASE:postgres}")
    private String dataSourceDatabase;

    @Value("${SPRING_DATASOURCE_USERNAME:postgres}")
    private String dataSourceUsername;

    @Value("${SPRING_DATASOURCE_PASSWORD:password}")
    private String dataSourcePassword;

    @Bean
    public ChatModel internalDocumentAiChatModel() {
        return GoogleAiGeminiChatModel.builder()
                .apiKey(googleAiGeminiApiKey)
                .modelName(googleAiGeminiModelName)
                .temperature(0.1)
                .topP(0.7)
                .topK(64)
                .timeout(Duration.ofSeconds(60))
                .logRequestsAndResponses(true)
                .build();
    }

    @Bean
    public ChatMemory internalDocumentAiChatMemory() {
        return MessageWindowChatMemory.withMaxMessages(10);
    }

    @Bean
    public EmbeddingModel internalDocumentEmbeddingModel() {
        return new BgeSmallEnV15QuantizedEmbeddingModel();
    }

    @Bean
    public EmbeddingStore<TextSegment> internalDocumentEmbeddingStore(EmbeddingModel internalDocumentEmbeddingModel) {
        return PgVectorEmbeddingStore.builder()
                .host(dataSourceHost)
                .port(dataSourcePort)
                .database(dataSourceDatabase)
                .user(dataSourceUsername)
                .password(dataSourcePassword)
                .table("internal_document_embeddings")
                .dimension(internalDocumentEmbeddingModel.dimension())
                .createTable(true)
                .dropTableFirst(true) // demo purposes only, should be false in production
                .build();
    }

    @Bean
    public EmbeddingStoreIngestor internalDocumentEmbeddingStoreIngestor(
            EmbeddingModel internalDocumentEmbeddingModel,
            EmbeddingStore<TextSegment> internalDocumentEmbeddingStore) {
        return EmbeddingStoreIngestor.builder()
                .embeddingModel(internalDocumentEmbeddingModel)
                .embeddingStore(internalDocumentEmbeddingStore)
                .documentSplitter(DocumentSplitters.recursive(2048, 256))
                .build();
    }

    @Bean
    public ContentRetriever internalDocumentEmbeddingStoreContentRetriever(
            EmbeddingModel internalDocumentEmbeddingModel,
            EmbeddingStore<TextSegment> internalDocumentEmbeddingStore) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingModel(internalDocumentEmbeddingModel)
                .embeddingStore(internalDocumentEmbeddingStore)
                .maxResults(4)
                .minScore(0.5)
                .build();
    }
}