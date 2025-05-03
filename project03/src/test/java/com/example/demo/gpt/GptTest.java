package com.example.demo.gpt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SpringBootTest
public class GptTest {
	
	@Value("${apikey}")
	String secretKey;
	
	@Test
	void 테스트() throws JSONException, IOException, InterruptedException {
		String apiKey = secretKey; // API 키를 입력하세요

        HttpClient client = HttpClient.newHttpClient();

        // 메시지 구성
        JSONArray messages = new JSONArray();
        JSONObject message = new JSONObject();
        // developer라는 역할은 없음
        // user, assistant, system 중에서 선택 
        // role과 질문을 변경
        message.put("role", "user");  // 올바른 역할은 "user", "assistant", 또는 "system"이어야 함
        message.put("content", "오늘의 운세가 뭐야?");
        messages.put(message);

        // 요청 본문
        JSONObject json = new JSONObject();
        json.put("model", "gpt-4.1");
        json.put("messages", messages);
        json.put("stream", false);  // store는 OpenAI에서 아직 사용되지 않는 필드입니다

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.openai.com/v1/chat/completions"))
            .header("Authorization", "Bearer " + apiKey)
            .header("Content-Type", "application/json")
            .POST(BodyPublishers.ofString(json.toString()))
            .build();

        // 응답 처리
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject responseBody = new JSONObject(response.body());

        // 응답 출력
        JSONArray choices = responseBody.getJSONArray("choices");
        JSONObject choice = choices.getJSONObject(0);
        System.out.println(choice.toString(2));
	}

}
