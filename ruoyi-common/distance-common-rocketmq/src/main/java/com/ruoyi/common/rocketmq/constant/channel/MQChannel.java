package com.ruoyi.common.rocketmq.constant.channel;


import com.baomidou.mybatisplus.core.toolkit.StringPool;

/**
 *<p>
 * 消息中心常量
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/9
 */
public class MQChannel {

	/**
	 * 生产者标识
	 */
	public static final String OUTPUT = "output";

	/**
	 * 消费者标识
	 */
	public static final String INPUT = "input";

	/**
	 * 短信消息
	 */
	public static final String SMS_MESSAGE = "sms";

	/**
	 * 订单消息
	 */
	public static final String ORDER_MESSAGE = "order";

	/**
	 * 消息生产者 sms-output  绑定
	 */
	public static final String SMS_MESSAGE_OUTPUT = SMS_MESSAGE + StringPool.DASH + OUTPUT;


	/**
	 * 订单生产者
	 */
	public static final String ORDER_MESSAGE_OUTPUT = ORDER_MESSAGE + StringPool.DASH + OUTPUT;




	/**
	 * 短信消费者  sms-output  绑定  +  监听
	 */
	public static final String SMS_MESSAGE_INPUT = SMS_MESSAGE + StringPool.DASH + INPUT;

	/**
	 * 订单消费者
	 */
	public static final String ORDER_MESSAGE_INPUT = ORDER_MESSAGE + StringPool.DASH + INPUT;




}
