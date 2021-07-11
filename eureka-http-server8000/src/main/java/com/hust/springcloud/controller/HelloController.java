package com.hust.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/consumer/hello")
    public String hello(){
        return "hello world";
    }
}
