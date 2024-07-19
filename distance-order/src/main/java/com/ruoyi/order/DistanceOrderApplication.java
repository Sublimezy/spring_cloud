package com.ruoyi.order;

import com.ruoyi.common.rocketmq.channel.DolphinSink;
import com.ruoyi.common.rocketmq.channel.DolphinSource;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@MapperScan({"com.ruoyi.order.mapper"})
@EnableRyFeignClients
@EnableBinding({DolphinSink.class, DolphinSource.class})
public class DistanceOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistanceOrderApplication.class, args);
    }

}
