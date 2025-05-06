package com.example.demo.gpt.controller;

import com.example.demo.gpt.dto.ChatResponse;
import com.example.demo.gpt.service.GptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GptController {

    @Autowired
    GptService service;

//    @ResponseBody
//    @GetMapping("/fortune")
//    public String fortune(){
//        return "api test...";
//    }

    @ResponseBody
    @GetMapping("/fortune")
    public String fortune(){
        String response = service.callGptApi();
        return response;
    }

}
