//package com.team5.sparcs.pico.controller;
//
//import com.team5.sparcs.pico.application.AiService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@RestController
//@RequiredArgsConstructor
//public class AiController {
//
//    private final AiService aiService;
//
//    @PostMapping("/test")
//    public String chatbot(@RequestBody Map<String, String> request) {
//        return aiService.chatbot(request.get("request"));
//    }
//
//}
//
///**
// *
// /**
// *
// * 1. s3 파일 업로드 기능 -> 가장 심플하게 하자
// *
// * 2. 과학자 -> 특징
// *
// * 3. 도감
// *
// * 4. 나랑 어울리는 과학자 찾아보기 보기  -> 보기 분석해서 프롬프트 만들기
// *
// * 5. 페이징 처리 -> 과학자
// *
// * 6. 요약 API
// *
// */
// */