package com.ruoyi.data;


import com.ruoyi.common.rocketmq.channel.DolphinSink;
import com.ruoyi.common.rocketmq.channel.DolphinSource;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;



@MapperScan({"com.ruoyi.data.mapper"})
@EnableRyFeignClients
@SpringBootApplication
@EnableBinding({DolphinSink.class, DolphinSource.class})
public class DistanceDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistanceDataApplication.class, args);
    }

}
