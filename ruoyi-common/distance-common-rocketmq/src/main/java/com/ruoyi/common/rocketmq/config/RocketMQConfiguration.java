package com.ruoyi.common.rocketmq.config;

import com.ruoyi.common.core.factory.YamlPropertySourceFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//开启本地配置

@Configuration
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:dolphin-rocketmq.yml")
@ComponentScan(basePackages = "com.ruoyi.common.rocketmq.constant.channel")
public class RocketMQConfiguration {

}
