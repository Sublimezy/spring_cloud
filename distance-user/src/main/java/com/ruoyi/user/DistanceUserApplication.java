package com.ruoyi.user;

import com.ruoyi.common.rocketmq.channel.DolphinSink;
import com.ruoyi.common.rocketmq.channel.DolphinSource;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;


@MapperScan({"com.ruoyi.user.mapper"})
@EnableRyFeignClients
@SpringBootApplication
@EnableBinding({DolphinSink.class, DolphinSource.class})
public class DistanceUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistanceUserApplication.class, args);
    }

}
