package com.example.order.controller;



import com.alibaba.csp.sentinel.annotation.SentinelResource;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.example.order.imp.StockService;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RefreshScope
@RestController
public class TestController {

//    @Autowired
//    private RestTemplate restTemplate;

    @Value("${user.name}")
    String name;

//    @RequestMapping("/order")
//    public String orderTest() throws Exception {
//
//        //String url = "http://localhost:8071/socket";
//        String url = "http://stock/socket";
//
//        //RestTemplate封装了Ribbon调用的过程，其中getForObject是最常用方法，同时还要在服务消费者中配置RestTemplate：
//        String re = restTemplate.getForObject(url,String.class);
//
//        System.out.println(name);
//        return re;
//    }


    @Autowired
    StockService stockService;

    //在skywalking中进行追踪
    @Trace
    //最终返回值和请求参数
    @Tags({@Tag(key="getAll",value="returnedObj"),
            @Tag(key="getAll",value="arg[0]")})
    @RequestMapping("/getStock")
    public String getStock() throws Exception {


        String re = stockService.getSocket("haha");

        System.out.println(re);
        return re;
    }



}
