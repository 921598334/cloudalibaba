package com.example.order.controller;

import com.example.order.service.HttpClientService;
import org.apache.http.NameValuePair;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @RequestMapping("/order")
    public String orderTest() throws Exception {

        String url = "要请求的url地址";
        /**
         * 参数值
         */
        Object [] params = new Object[]{"param1","param2"};
        /**
         * 参数名
         */
        Object [] values = new Object[]{"value1","value2"};
        /**
         * 获取参数对象
         */
        List<NameValuePair> paramsList = HttpClientService.getParams(params, values);
        /**
         * 发送get
         */
        Object result = HttpClientService.sendGet(url, paramsList);
        /**
         * 发送post
         */
        Object result2 = HttpClientService.sendPost(url, paramsList);

        System.out.println("GET返回信息：" + result);
        System.out.println("POST返回信息：" + result2);

        return "order";
    }
}
