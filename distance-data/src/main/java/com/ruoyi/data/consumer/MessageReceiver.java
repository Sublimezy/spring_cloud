package com.ruoyi.data.consumer;

import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {


    /**
     * 监控听Order  input
     * @param message 消费文本内容
     */
/*    @StreamListener(MessageConstant.ORDER_MESSAGE_INPUT)
    public void handler(@Payload String message) {
        System.out.println("当前为数据模块 订单接收消息: " + message);
    }*/
    /**
     * 消费分布式事务消息
     * 注意,下游服务不能回滚事务,只要上游服务提交了事务,下游服务必须成功,如果下游服务失败了,就只能进行人工补偿
     * 如果想要保证下游服务事务失败后可以回滚上游服务事务,这边也提供了解决方案,请参考Seata分布式事务实例
     */
/*    @StreamListener(MQChannel.ORDER_MESSAGE_INPUT)
    @Transactional(rollbackFor = Exception.class)
    public void orderHandle(Message<?> message) {


        TestOrder testOrder = (TestOrder) message.getPayload();

        MessageHeaders headers = message.getHeaders();

        String transactionId = headers.get(RocketMQHeaders.TRANSACTION_ID, String.class);
        String shareId = headers.get("share_id", String.class);

        System.out.println("当前为数据模块 half订单接收消息: " + testOrder);
        System.out.println("Transaction ID: " + transactionId);
        System.out.println("Share ID: " + shareId);

        System.out.println("当前为数据模块 harf订单接收消息: " + testOrder);
    }*/


}
