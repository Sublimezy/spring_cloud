package com.ruoyi.common.rocketmq.channel;


import com.ruoyi.common.rocketmq.constant.channel.MQChannel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;


@Component
public interface DolphinSource {

	/**
	 * 消息通道
	 *
	 * @return MessageChannel
	 */
	//绑定了   主题和群组
	@Output(MQChannel.SMS_MESSAGE_OUTPUT)
	MessageChannel smsOutput();

	/**
	 * 订单通道
	 *
	 * @return MessageChannel
	 */
	@Output(MQChannel.ORDER_MESSAGE_OUTPUT)
	MessageChannel orderOutput();





}
