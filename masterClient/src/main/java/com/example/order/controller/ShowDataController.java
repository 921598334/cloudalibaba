package com.example.order.controller;


import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Controller
public class ShowDataController {


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/getData")
    @ResponseBody
    public void getData() throws IOException, InterruptedException {

        System.out.println("============getData 开始==============");

        //然后存储再redis中
        ZSetOperations<String, String> valueOperations = stringRedisTemplate.opsForZSet();


        while (true){

            //获取key来判断当前的下位机和通道
            Set<String> keys = stringRedisTemplate.keys("pointDataAfterList_*");

            System.out.println("keys:"+JSON.toJSONString(keys));

            Set<String> salveIdSet = new HashSet<String>();
            Set<String> passIdSet = new HashSet<String>();

            for(String key:keys){
                String[] keyBody = key.split("_");
                salveIdSet.add(keyBody[1]);
                passIdSet.add(keyBody[2]);
            }


            for(String salve:salveIdSet){
                for(String passId:passIdSet){

                    //获取某个下位机，某个通道最新的元素
                    String key = "pointDataAfterList_" + salve + "_" + passId;

                    Set<String> lastDataSet = valueOperations.range(key,0,0);

                    if(lastDataSet==null || lastDataSet.size()==0){
                        continue;
                    }


                    String lastData = lastDataSet.toArray()[0].toString();

                    //删除
                    valueOperations.remove(key,lastData);



                    //进行展示
                    System.out.println(lastData);

                }
            }

        }



    }



}
