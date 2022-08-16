package com.example.order.imp;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



//name表示要请求的服务，path请求的url
@FeignClient(name="stock",path="/")
public interface StockService {

    // 声明需要调用的rest接口对应的方法
    @RequestMapping("/getSocket")
    String getSocket(@RequestParam(name = "orderId", defaultValue = "") String orderId);

}
