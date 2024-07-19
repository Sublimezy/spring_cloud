package com.ruoyi.order.consumer;


import com.ruoyi.common.rocketmq.constant.channel.MQChannel;
import com.ruoyi.common.rocketmq.constant.tags.OrderTagsEnum;
import com.ruoyi.common.rocketmq.util.MQMessage;
import com.ruoyi.order.entity.dos.UserOrder;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderConsumer {




    @Transactional(rollbackFor = Exception.class)
    @StreamListener(target = MQChannel.ORDER_MESSAGE_INPUT)
    public void handleMessage(Message<String> message) {

        MQMessage mqMessage = new MQMessage();

        String tags = mqMessage.getTags(message);

        String transactionId = mqMessage.getTransactionId(message);

        UserOrder userOrder = mqMessage.getParsePayload(message, UserOrder.class);

        // 处理接收到的消息
        System.out.println("我的订单 " + userOrder);
        System.out.println("Tags: " + tags);
        System.out.println("Transaction ID: " + transactionId);

        // 进一步处理消息，例如根据标签执行不同的业务逻辑
        switch (OrderTagsEnum.valueOf(tags)) {
            case ORDER_CREATE:
                System.out.println("============开始处理订单创建消息============");
                break;


            case STATUS_CHANGE:
                System.out.println("============开始处理订单变化消息============");
                break;
        }


    }


}

