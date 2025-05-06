package com.example.demo.gpt;

import com.example.demo.gpt.dto.ChatResponse;
import com.example.demo.gpt.service.GptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GptServiceTest {

    @Autowired
    GptService service;

    @Test
    void API호출테스트(){
        ChatResponse response = service.callGptApi();
        ChatResponse.Message msg = response.getMessage();
        System.out.println(msg);
    }

}
