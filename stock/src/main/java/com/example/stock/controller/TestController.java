package com.example.stock.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${server.port}")
    String port;

    @RequestMapping("/socket")
    public String orderTest(){

        return "serverport:"+port;
    }
}
