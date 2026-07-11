# rag-document-qa-springboot
RAG-powered Document Q&amp;A application using Java, Spring Boot, LangChain4j, and Ollama
# RAG Document Q&A — Spring Boot + LangChain4j

AI-powered document question answering system built with:
- Java 17 + Spring Boot 3.2
- LangChain4j 0.36.2
- OpenAI text-embedding-3-small
- AllMiniLmL6V2 local embeddings
- InMemoryEmbeddingStore
- REST API endpoints

## Architecture
Upload PDF → Chunk → Embed → Store
Question → Embed → Retrieve → LLM → Answer

## Endpoints
POST /api/documents/upload
POST /api/chat
POST /api/documents/text
