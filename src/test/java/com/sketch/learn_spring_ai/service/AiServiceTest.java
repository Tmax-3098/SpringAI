package com.sketch.learn_spring_ai.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AiServiceTest {

    @Autowired
    private AiService aiService;

    @Test
    public void getJoke(){
        String topic = "blackpink";
        String output = aiService.getJoke(topic);
        System.out.println(output);
    }
}
