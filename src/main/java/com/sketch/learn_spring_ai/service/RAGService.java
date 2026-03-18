package com.sketch.learn_spring_ai.service;

import lombok.RequiredArgsConstructor;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RAGService {

    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;

    @Value("classpath:F1_2026_Teams_Overview.pdf")
    Resource pdfFile;

    public String testAI(String prompt){
        String template = """
                You are an AI assistant helping a developer.
                
                Rules:
                - Use Only the information provided in the context
                - You MAY rephrase, summarize, and explain in natural language
                - Do NOT introduce new concepts or facts
                - If multiple context sections are relevant, combine them into a single explanation.
                - If the answer is not present, say I dont know.
                
                Context:
                {context}
                
                Answer in a friendly, conversational tone.
                """;


        List<Document> documents = vectorStore.similaritySearch(SearchRequest.builder()
                .query(prompt)
                .topK(2)
                .filterExpression("file_name == 'F1_2026_Teams_Overview.pdf'")
                .build());

        String context = documents.stream().map(
                doc -> doc.getText()
        ).collect(Collectors.joining("\n\n"));
        PromptTemplate promptTemplate = new PromptTemplate(template);
        String systemPrompt = promptTemplate.render(Map.of("context", context));

        return chatClient.prompt()
                .system(systemPrompt)
                .user(prompt)
                .advisors(
                        new SimpleLoggerAdvisor()
                )
                .call()
                .content();
    }

    public void ingestPdfToVectorStore(){
        PagePdfDocumentReader reader = new PagePdfDocumentReader(pdfFile);
        List<Document> pages = reader.get();

        TokenTextSplitter tokenTextSplitter = TokenTextSplitter.builder()
                .withChunkSize(200)
                .build();
        List<Document> chunks = tokenTextSplitter.apply(pages);
        vectorStore.add(chunks);

    }
}
