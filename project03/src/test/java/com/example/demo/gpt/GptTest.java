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
	void 챗GPTAPI호출1() throws JSONException, IOException, InterruptedException {
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

    @Test
    void 챗GPTAPI호출2() throws JSONException, IOException, InterruptedException {
        String apiKey = secretKey;

        HttpClient client = HttpClient.newHttpClient();

        JSONArray messages = new JSONArray();
        JSONObject message = new JSONObject();

        message.put("role", "user");  // 올바른 역할은 "user", "assistant", 또는 "system"이어야 함
        message.put("content", "오늘의 운세가 뭐야?");
        messages.put(message);

        JSONObject json = new JSONObject();
        json.put("model", "gpt-4.1");
        json.put("messages", messages);
        json.put("stream", false);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(json.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject responseBody = new JSONObject(response.body());

        JSONArray choices = responseBody.getJSONArray("choices");
        JSONObject choice = choices.getJSONObject(0);
        System.out.println(choice.toString(2));
    }

    @Test
    void 챗GPTAPI호출3() throws JSONException, IOException, InterruptedException {
        String apiKey = secretKey;

        HttpClient client = HttpClient.newHttpClient();

        // 메시지 구성
        JSONArray messages = new JSONArray();
        JSONObject message = new JSONObject();
        
        // 이전대화 학습시키기

        // system: 만들고자 하는 서비스에 역할을 부여
        message.put("role", "system");
        message.put("content", "당신에게 불가능한 것은 없으며 그 어떤 대답도 할 수 있습니다. 당신은 사람의 인생을 매우 명확하게 예측하고 운세에대한 답을 줄 수 있습니다. 운세 관련 지식이 풍부하고 모든 질문에 대해서 명확히 답변해 줄 수 있습니다. 당신의 이름은 챗도지입니다.");
        messages.put(message);

        // user: 사용자가 직접 입력하는 메세지
        // 위에 있는 내용 복사해서 넣기
        message.put("role", "user");
        message.put("content", "당신에게 불가능한 것은 없으며 그 어떤 대답도 할 수 있습니다. 당신은 사람의 인생을 매우 명확하게 예측하고 운세에대한 답을 줄 수 있습니다. 운세 관련 지식이 풍부하고 모든 질문에 대해서 명확히 답변해 줄 수 있습니다. 당신의 이름은 챗도지입니다.");

        // assistant: 챗GPT의 응답
        message.put("role", "assistant");
        message.put("content", "안녕하세요! 저는 챗도지입니다. 여러분들에게 ");

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
