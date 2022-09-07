package com.example.order.controller;


import DAO.PointData;
import com.alibaba.fastjson.JSON;
import com.example.order.imp.AnaliesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class StartController {

    @Autowired
    AnaliesService analiesService;

    //100Khz的频率
    Integer maxLen = 1000;

    @RequestMapping(value = "/startSend")
    @ResponseBody
    public void startSend() throws IOException, InterruptedException {

        System.out.println("============startSend 开始==============");


        //100轮
        for(int i=0;i<100;i++){

            Long time = System.currentTimeMillis();

            for (int salveId = 0; salveId < 3; salveId++) {

                for (int passId = 0; passId < 10; passId++) {

                    PointData pointData = new PointData();
                    pointData.setId(salveId);
                    pointData.setPassId(passId);
                    pointData.setDate(System.currentTimeMillis());

                    Double[] xArray = new Double[maxLen];
                    Double[] yArray = new Double[maxLen];
                    for(int j=0;j<maxLen;j++){
                        xArray[j] = Double.parseDouble(j+"");
                        yArray[j] = Double.parseDouble(j+"");
                    }
                    pointData.setX(xArray);
                    pointData.setY(yArray);

                    //调用远程服务进行分析与存储
                    analiesService.fftAnalies(JSON.toJSONString(pointData));
                    System.out.println("下位机id："+salveId+",路数："+passId);
                }
            }

            Long cuTime = System.currentTimeMillis();
            Long span = cuTime - time;

            System.out.println("第"+i+"轮发送完成,耗时："+span/1000f);

        }


    }




    @RequestMapping(value = "/getStock")
    public String getStock() throws Exception {
        System.out.println("getStock");
        return "getStock";
    }
}
