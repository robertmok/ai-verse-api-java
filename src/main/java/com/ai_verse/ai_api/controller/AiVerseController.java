package com.ai_verse.ai_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai-verse")
public class AiVerseController {

    @GetMapping("/hello")
    public String hello() {

        return "Hello, World!";
    }

}
