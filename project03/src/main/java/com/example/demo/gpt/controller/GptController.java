package com.example.demo.gpt.controller;

import com.example.demo.gpt.dto.ChatRequest;
import com.example.demo.gpt.dto.ChatResponse;
import com.example.demo.gpt.service.GptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GptController {

    @Autowired
    GptService service;

//    @ResponseBody
//    @GetMapping("/fortuneTell")
//    public String fortuneTell(){
//        return "api test...";
//    }

    @ResponseBody
    @PostMapping("/fortuneTell")
    public String fortuneTell(@RequestBody ChatRequest request){
    	
    	System.out.println(request);
    	
        String response = service.callGptApi(request);
        return response;
    }
    
    // 화면을 반환하는 메소드
    @GetMapping("/fortune")
    public String fortune(){
        return "/index";
    }

}
