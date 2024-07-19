package com.ruoyi.email.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@ConfigurationProperties(prefix = "distance.data.rocketmq")
@Data
public class RocketMQCustomProperties {
    private String smsOutput;
    private String smsInput;
    private String smsTopic;
    private String smsGroup;
    private String orderOutput;
    private String orderInput;
    private String orderTopic;
    private String orderGroup;
}
