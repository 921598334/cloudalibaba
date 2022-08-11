package com.example.order.controller;

import com.example.order.service.HttpClientService;
import org.apache.http.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/order")
    public String orderTest() throws Exception {

        //String url = "http://localhost:8071/socket";
        String url = "http://stock/socket";

        //RestTemplate封装了Ribbon调用的过程，其中getForObject是最常用方法，同时还要在服务消费者中配置RestTemplate：
        String re = restTemplate.getForObject(url,String.class);

        return re;
    }
}
