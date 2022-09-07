package com.example.order.imp;




import DAO.PointData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


//name表示要请求的服务，path请求的url
@FeignClient(name="analies-service1",path="/")
public interface AnaliesService {

    // 声明需要调用的rest接口对应的方法
    @RequestMapping("/fftAnalies")
    String fftAnalies(@RequestParam(name = "pointData", defaultValue = "") String pointData);

}
