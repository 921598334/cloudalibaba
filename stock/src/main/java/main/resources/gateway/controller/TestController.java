package main.resources.gateway.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TestController {

    @Value("${server.port}")
    String port;

    @RequestMapping("/socket")
    public String orderTest(){

        return "serverport:"+port;
    }



    @RequestMapping("/loginErr")
    public String login(){

        return "需要进行登录";
    }



    @RequestMapping("/getSocket")
    public String getSocket(@RequestParam(name="orderId",defaultValue="") String orderId) throws IOException {

        System.out.println("============getSocket 开始==============");

        System.out.println(orderId);

        return orderId+" "+port;
    }
}
