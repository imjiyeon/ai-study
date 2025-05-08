package com.example.demo.gpt.dto;

import lombok.*;

import java.util.List;
import java.util.Stack;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRequest {
	
	// 사용자 메세지 (목록)
	List<String> userMsg;
	
	// 챗봇 응답 (목록)
	List<String> botMsg;

}

