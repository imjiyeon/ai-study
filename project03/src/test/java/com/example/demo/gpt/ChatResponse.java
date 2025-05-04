package com.example.demo.gpt;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatResponse {
    private String finish_reason;
    private int index;
    private Message message; // 답변
    private Object logprobs;

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Message {
        private String role;
        private String content;
        private Object refusal;
        private List<Object> annotations;
    }

}

