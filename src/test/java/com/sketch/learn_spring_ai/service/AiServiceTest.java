package com.sketch.learn_spring_ai.service;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class AiServiceTest {

    @Autowired
    private AiService aiService;

    @Test
    public void testAiServiceGetDesign(){
        String topic = "rearwing";
        String output = aiService.getJoke(topic);
        System.out.println(output);
    }

//    @Test
//    public void testAiServiceGetEmbedding(){
//        float[] embeddings = aiService.getEmbedding("You know What that is ? Simply Lovely");
//        System.out.println(Arrays.toString(embeddings));
//        System.out.println(embeddings.length);
//    }

//    @Test
//    public void testAiServiceInjestData(){
//        aiService.ingestDataToVectorStore();
//    }

    @Test
    public void testSimilaritySearch(){
        List<Document> result = aiService.similaritySearch("space movie");
        for(var doc: result){
            System.out.println(doc);
        }
    }
}
