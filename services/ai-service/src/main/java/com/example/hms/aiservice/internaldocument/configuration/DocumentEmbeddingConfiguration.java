package com.example.hms.aiservice.internaldocument.configuration;

import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentEmbeddingConfiguration {

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
    public EmbeddingModel documentEmbeddingModel() {
        return new BgeSmallEnV15QuantizedEmbeddingModel();
    }

    @Bean
    public EmbeddingStore<TextSegment> documentEmbeddingStore(EmbeddingModel documentEmbeddingModel) {
        return PgVectorEmbeddingStore.builder()
                .host(dataSourceHost)
                .port(dataSourcePort)
                .database(dataSourceDatabase)
                .user(dataSourceUsername)
                .password(dataSourcePassword)
                .table("document_embeddings")
                .dimension(documentEmbeddingModel.dimension())
                .createTable(true)
                .dropTableFirst(false)
                .build();
    }

    @Bean
    public EmbeddingStoreIngestor documentEmbeddingStoreIngestor(
            EmbeddingModel documentEmbeddingModel,
            EmbeddingStore<TextSegment> documentEmbeddingStore) {
        return EmbeddingStoreIngestor.builder()
                .embeddingModel(documentEmbeddingModel)
                .embeddingStore(documentEmbeddingStore)
                .documentSplitter(DocumentSplitters.recursive(500, 50))
                .build();
    }

    @Bean
    public ContentRetriever documentContentRetriever(
            EmbeddingModel documentEmbeddingModel,
            EmbeddingStore<TextSegment> documentEmbeddingStore) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingModel(documentEmbeddingModel)
                .embeddingStore(documentEmbeddingStore)
                .maxResults(5)
                .minScore(0.5)
                .build();
    }
}