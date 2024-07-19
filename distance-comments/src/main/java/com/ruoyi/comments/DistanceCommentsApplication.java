package com.ruoyi.comments;

import com.ruoyi.common.rocketmq.channel.DolphinSink;
import com.ruoyi.common.rocketmq.channel.DolphinSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@MapperScan({"com.ruoyi.comments.mapper"})
@EnableBinding({DolphinSink.class, DolphinSource.class})
public class DistanceCommentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistanceCommentsApplication.class, args);
    }

}
