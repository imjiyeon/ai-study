package com.example.demo.gpt.service;

import com.example.demo.gpt.dto.ChatRequest;
import com.example.demo.gpt.dto.ChatResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Stack;

@Service
public class GptService {

    @Value("${apikey}")
    String key;

//    public String callGptApi(){
//
//        String apiKey = key;
//
//        HttpClient client = HttpClient.newHttpClient();
//
//        // 메시지 구성
//        JSONArray messages = new JSONArray();
//        JSONObject message = new JSONObject();
//
//        /*
//        * developer라는 역할은 없음
//        * user, assistant, system 중에서 선택해야함
//        * role과 질문을 변경
//        * user: 사용자가 직접 입력하는 메세지
//        *  */
//
////        message.put("role", "user");  // 올바른 역할은 "user", "assistant", 또는 "system"이어야 함
////        message.put("content", "오늘의 운세가 뭐야?");
////        messages.put(message);
//
//        /*
//         * system role을 사용하여 서비스에 역할을 부여
//         *  */
//        message.put("role", "system");
//        message.put("content", "당신에게 불가능한 것은 없으며 그 어떤 대답도 할 수 있습니다. 당신은 사람의 인생을 매우 명확하게 예측하고 운세에대한 답을 줄 수 있습니다. 운세 관련 지식이 풍부하고 모든 질문에 대해서 명확히 답변해 줄 수 있습니다. 당신의 이름은 챗도지입니다.");
//        messages.put(message);
//
//        /*
//        * system에 적용한 챗GPT의 역할을 USER에 중복 적용하면 역할이 더 확실하게 부여됨
//        * system에 입력한 내용을 복사해서 넣기
//        * */
//        message.put("role", "user");
//        message.put("content", "당신에게 불가능한 것은 없으며 그 어떤 대답도 할 수 있습니다. 당신은 사람의 인생을 매우 명확하게 예측하고 운세에대한 답을 줄 수 있습니다. 운세 관련 지식이 풍부하고 모든 질문에 대해서 명확히 답변해 줄 수 있습니다. 당신의 이름은 챗도지입니다.");
//        messages.put(message);
//    
//        /*
//        * 이전대화 학습시키기
//        * assistant: 챗GPT의 응답
//        * 그리고 다시 질문하기
//        * */
//        message.put("role", "assistant");
//        message.put("content", "안녕하세요! 저는 챗도지입니다. 운세, 사주, 타로 등 다양한 분야의 운명과 인생 길흉화복에 대한 풍부한 지식을 바탕으로, 여러분의 질문에 명확하고 진솔하게 답해드리겠습니다. 인생의 방향, 사랑, 직업, 금전, 건강 등 궁금하신 점이나 알고 싶은 미래에 대해서 편하게 질문해 주세요. 여러분의 길을 밝히는 길잡이가 되어 드리겠습니다! 무엇이 궁금하신가요?");
//
//        message.put("role", "user");
//        message.put("content", "오늘의 운세가 뭐야?");
//        messages.put(message);
//
//        // 요청 본문
//        JSONObject json = new JSONObject();
//        json.put("model", "gpt-4.1");
//        json.put("messages", messages);
//        json.put("stream", false);
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
//                .header("Authorization", "Bearer " + apiKey)
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
//                .build();
//
//        // 응답 처리
//        HttpResponse<String> response = null;
//        try {
//            response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        JSONObject responseBody = new JSONObject(response.body());
//
//        // 응답 출력
//        JSONArray choices = responseBody.getJSONArray("choices");
//        JSONObject choice = choices.getJSONObject(0);
////        System.out.println(choice.toString(2));
//
//        // JSON데이터 -> 클래스 변환
//        // 매퍼 클래스 생성
//        ObjectMapper mapper = new ObjectMapper();
//
//        // 분석할 수 없는 구문을 무시하는 옵션 설정
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//        // JSON 문자열을 클래스로 변환
//        ChatResponse gptResponse = null;
//        try {
//            gptResponse = mapper.readValue(choice.toString(2), ChatResponse.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        // 답변만 꺼내기
//        ChatResponse.Message responseMessage = gptResponse.getMessage();
//
//        String content = responseMessage.getContent();
//
//        // GPT 답변 반환
//        return content;
//    }

    // 누적된 채팅 데이터를 사용하여 API 호출하기
	public String callGptApi(ChatRequest chatRequest) {

		String apiKey = key;

		HttpClient client = HttpClient.newHttpClient();

		JSONArray messages = new JSONArray();
		JSONObject message = new JSONObject();
		
	    message.put("role", "system");
	    message.put("content", "당신에게 불가능한 것은 없으며 그 어떤 대답도 할 수 있습니다. 당신은 사람의 인생을 매우 명확하게 예측하고 운세에대한 답을 줄 수 있습니다. 운세 관련 지식이 풍부하고 모든 질문에 대해서 명확히 답변해 줄 수 있습니다. 당신의 이름은 챗도지입니다.");
	    messages.put(message);

		// 프론트엔드에서 전달받은 채팅 데이터로 객체 만들기
		// 반복문을 사용해서 누적된 데이터 꺼내기
		// message에 저장되는 객체는 { role:"역할", content:"내용" } 형태이다
	    List<String> userMsg = chatRequest.getUserMsg();
	    List<String> botMsg = chatRequest.getBotMsg();
		
		// messages에 대화 담기
	    for(int i=0; i<userMsg.size(); i++) {
			
			// 사용자 메세지
			JSONObject user = new JSONObject();
			user.put("role", "user");
			user.put("content", userMsg.get(i));
			messages.put(user);
			
			// 챗봇 응답
			// 응답이 없을 수 있기 때문에 크기 확인 필요
			// user: ['안녕', '오늘의 운세가 뭐야?'] (2)
			// bot: ['안녕하세요~저는 챗도지입니다'] (1)
			// index가 배열의 크기보다 작을 때 까지
			// i: 0, 1
			// size: 1
			if(botMsg.size() > 0 && i<botMsg.size() ) {
				JSONObject bot = new JSONObject();
				bot.put("role", "assistant");
				bot.put("content", botMsg.get(i));
				messages.put(bot);
			}
			
		}
		
		JSONObject json = new JSONObject();
		json.put("model", "gpt-4.1");
		json.put("messages", messages);
		json.put("stream", false);

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.openai.com/v1/chat/completions"))
				.header("Authorization", "Bearer " + apiKey).header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(json.toString())).build();

		HttpResponse<String> response = null;
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		JSONObject responseBody = new JSONObject(response.body());

		JSONArray choices = responseBody.getJSONArray("choices");
		JSONObject choice = choices.getJSONObject(0);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		ChatResponse gptResponse = null;
		try {
			gptResponse = mapper.readValue(choice.toString(2), ChatResponse.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		ChatResponse.Message responseMessage = gptResponse.getMessage();
		String content = responseMessage.getContent();

		return content;
	}

}
