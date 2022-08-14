package com.example.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableFeignClients
public class OrderApplication {

    public static void main(String[] args) throws InterruptedException {

        //SpringApplication.run(OrderApplication.class, args);

        ConfigurableApplicationContext applicationContext = SpringApplication.run(OrderApplication.class, args);
        while (true) {
            String userName = applicationContext.getEnvironment().getProperty("user.name");
            String userAge = applicationContext.getEnvironment().getProperty("user.age");
            String config = applicationContext.getEnvironment().getProperty("user.config");
            System.err.println("user name :" + userName + "; age: " + userAge+";config:"+config);
            TimeUnit.SECONDS.sleep(1);
        }
        // nacos客户端 每10ms去 注册中心进行判断，  根据MD5
    }





//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplate(RestTemplateBuilder builder){
//        RestTemplate restTemplate1 = builder.build();
//        return restTemplate1;
//    }
}
