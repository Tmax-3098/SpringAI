package com.sketch.learn_spring_ai.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RAGServiceTest {

    @Autowired
    private RAGService ragService;

//    @Test
//    public void injestVectorStoreTest(){
//        ragService.ingestPdfToVectorStore();
//    }

    @Test
    public void testRAG(){
        String res = ragService.testAI("tell me about redbull");
        System.out.println(res);
    }

}
