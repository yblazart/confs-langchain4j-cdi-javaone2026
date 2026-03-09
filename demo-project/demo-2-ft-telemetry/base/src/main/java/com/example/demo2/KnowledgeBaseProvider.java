package com.example.demo2;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * CDI producer for the RAG ContentRetriever.
 * Ingests conference session data into an in-memory embedding store at startup.
 */
@ApplicationScoped
public class KnowledgeBaseProvider {

    @Inject
    ConferenceRepository repository;

    @Produces
    @Named("my-rag")
    @ApplicationScoped
    public ContentRetriever contentRetriever() {
        OllamaEmbeddingModel embeddingModel = OllamaEmbeddingModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("qwen2.5:7b")
                .build();

        InMemoryEmbeddingStore<TextSegment> store = new InMemoryEmbeddingStore<>();

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(store)
                .build();

        for (ConferenceSession session : repository.listAll()) {
            ingestor.ingest(Document.from(session.toRagDocument(),
                    Metadata.from("sessionId", session.getId())));
        }

        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(store)
                .embeddingModel(embeddingModel)
                .maxResults(3)
                .build();
    }
}
