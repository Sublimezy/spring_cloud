package com.ruoyi.common.rocketmq.channel;


import com.ruoyi.common.rocketmq.constant.channel.MQChannel;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;


public interface DolphinSink {

	/**
	 * 短消息消费者
	 *定义通道名称
	 */
	@Input(MQChannel.SMS_MESSAGE_INPUT)
	SubscribableChannel smsInput(); // 声明通道方法


	/**
	 * 订单消费者
	 *
	 * @return SubscribableChannel
	 */
	@Input(MQChannel.ORDER_MESSAGE_INPUT)
	SubscribableChannel orderInput();
}
