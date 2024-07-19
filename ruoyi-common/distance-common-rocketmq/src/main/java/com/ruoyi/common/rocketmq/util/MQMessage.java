package com.ruoyi.common.rocketmq.util;


import com.alibaba.fastjson.JSON;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class MQMessage {


    public String getTags(Message<String> message) {
        return message.getHeaders().get("ROCKET_TAGS", String.class);
    }

    public String getTransactionId(Message<String> message) {
        return message.getHeaders().get("TRANSACTION_ID", String.class);
    }

    public  <T> T getParsePayload(Message<String> message, Class<T> clazz) {
        String jsonString = message.getPayload();
        return JSON.parseObject(jsonString, clazz);
    }

}
