package com.dawnwar.rabbitmq;

import javax.annotation.Resource;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ消息发送
 */

@Component
public class RabbitMqSender {

	@Resource
	private AmqpTemplate amqpTemplate;

	/**
	 * 发送消息 默认exchange
	 * 
	 * @param routingKey
	 *            路由key
	 * @param data
	 *            数据
	 */
	public void send(String routingKey, String data) {
		Message message = MessageBuilder.withBody(data.getBytes()).setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN).build();
		amqpTemplate.send(routingKey, message);
	}

	/**
	 * 发送消息
	 * 
	 * @param exchange
	 *            交换器名称
	 * @param routingKey
	 *            路由key
	 * @param data
	 *            数据
	 */
	public void send(String exchange, String routingKey, String data) {
		Message message = MessageBuilder.withBody(data.getBytes()).setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN).build();
		amqpTemplate.send(exchange, routingKey, message);
	}
}
