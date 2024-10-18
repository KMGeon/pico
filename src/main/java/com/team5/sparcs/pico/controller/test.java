package com.team5.sparcs.pico.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {
    @GetMapping("/v1")
    public String test(){
        return "1";
    }
}
