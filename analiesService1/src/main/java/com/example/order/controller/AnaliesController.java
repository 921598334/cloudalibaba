package com.example.order.controller;



import DAO.PointData;
import com.alibaba.fastjson.JSON;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
public class AnaliesController {


    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate redisTemplate;


    //在skywalking中进行追踪
    @Trace
    //追踪返回值和请求参数
    @Tags({@Tag(key="fftAnalies",value="returnedObj"),
            @Tag(key="fftAnalies",value="arg[0]")})
    @RequestMapping("/fftAnalies")
    public void fftAnalies(@RequestParam(name="pointData",defaultValue="") String pointData) throws IOException, InterruptedException {

        System.out.println("============fftAnalies 开始==============");


        //开启多线程进行分析,假设分析500毫秒
        //Thread.sleep(500);

        PointData pointDataTmp = JSON.parseObject(pointData, PointData.class);

        //然后存储再redis中
        ZSetOperations<String, String> valueOperations = stringRedisTemplate.opsForZSet();

        valueOperations.add("pointDataAfterList_"+pointDataTmp.getId()+"_"+pointDataTmp.getPassId(),pointData,pointDataTmp.getDate());

        System.out.println("插入成功");
    }

}
