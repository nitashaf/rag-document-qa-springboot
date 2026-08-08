package com.neuroforge.rag.config;

import org.springframework.context.annotation.Configuration;
import dev.langchain4j.model.embedding.onnx.allminilml6v2q.AllMiniLmL6V2QuantizedEmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import org.springframework.context.annotation.Bean;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import org.springframework.context.annotation.Primary;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;

@Configuration
public class RagConfig {
	
	@Bean
	@Primary
	public EmbeddingModel embeddingModel() {
	    return new AllMiniLmL6V2QuantizedEmbeddingModel();
	}
	@Bean
	public EmbeddingStore<TextSegment> embeddingStore() {
	    return new InMemoryEmbeddingStore<>();
	}
	
	@Bean
	public EmbeddingStoreIngestor ingestor(EmbeddingModel embeddingModel,
	                                       EmbeddingStore<TextSegment> embeddingStore) {
	    return EmbeddingStoreIngestor.builder()
	            .documentSplitter(DocumentSplitters.recursive(400, 80))
	            .embeddingModel(embeddingModel)
	            .embeddingStore(embeddingStore)
	            .build();
	}
	
	@Bean
	public ContentRetriever contentRetriever(EmbeddingStore<TextSegment> embeddingStore,
	                                         EmbeddingModel embeddingModel) {
	    return EmbeddingStoreContentRetriever.builder()
	            .embeddingStore(embeddingStore)
	            .embeddingModel(embeddingModel)
	            .maxResults(5)
	            .minScore(0.55)
	            .build();
	}

}