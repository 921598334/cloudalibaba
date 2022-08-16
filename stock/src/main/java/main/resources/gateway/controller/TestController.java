package main.resources.gateway.controller;


import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TestController {

    @Value("${server.port}")
    String port;

    //在skywalking中进行追踪
    @Trace
    //追踪返回值和请求参数
    @Tags({@Tag(key="getAll",value="returnedObj"),
            @Tag(key="getAll",value="arg[0]")})
    @RequestMapping("/socket")
    public String orderTest(){

        return "serverport:"+port;
    }


    //在skywalking中进行追踪
    @Trace
    //追踪返回值和请求参数
    @Tags({@Tag(key="getAll",value="returnedObj"),
            @Tag(key="getAll",value="arg[0]")})
    @RequestMapping("/loginErr")
    public String login(){

        return "需要进行登录";
    }


    //在skywalking中进行追踪
    @Trace
    //追踪返回值和请求参数
    @Tags({@Tag(key="getAll",value="returnedObj"),
            @Tag(key="getAll",value="arg[0]")})
    @RequestMapping("/getSocket")
    public String getSocket(@RequestParam(name="orderId",defaultValue="") String orderId) throws IOException {

        System.out.println("============getSocket 开始==============");

        System.out.println(orderId);

        return orderId+" "+port;
    }
}
