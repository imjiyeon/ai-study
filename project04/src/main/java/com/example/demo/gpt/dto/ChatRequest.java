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
	
	// list,map 같은 stack 자료구조
	// 데이터를 꺼낼 때, 나중에 추가된 데이터를 먼저 꺼냄
	Stack<String> userMsg;
	
	Stack<String> assistantMsg;

}

