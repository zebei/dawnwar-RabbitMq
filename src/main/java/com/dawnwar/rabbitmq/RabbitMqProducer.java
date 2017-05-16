package com.dawnwar.rabbitmq;




import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * RabbiMQ消息producer
 * 2017/3
 */
@Slf4j
@Component
public class RabbitMqProducer {
	@Autowired
	private RabbitMqSender rabbitMqSender;
	
	/**
	 * 消息发送
	 * @param id 数据id
	 * @param exchange exchange名称
	 * @param type routingKey固定部分
	 * @param action 操作类型
	 * @param data 发送数据
	 */
	public void sendMessage(String id, String exchange, String type, SubmitAction action, Object data) {
		// 发送到RabbitMQ
		if (StringUtils.isBlank(type)) {
			return;
		}
		JSONObject dataJson = JSONObject.parseObject(JSON.toJSONString(data));
		JSONObject sendMsg = new JSONObject();
		sendMsg.put("id", id);
		sendMsg.put("type", type);
		// 路由key
		String routingKey = type + "." + action.name();
		sendMsg.put("action", action.name());
		sendMsg.put("send_time", System.currentTimeMillis());
		sendMsg.put("data", dataJson);

		rabbitMqSender.send(exchange, routingKey, sendMsg.toJSONString());
		log.info("Exchanger:{} routingKey:{} rabbitMQ message:{} send succeed",
				exchange, routingKey, sendMsg);
	}
	
	
	/**
	 * 消息发送
	 * @param id 数据id
	 * @param exchange exchange名称,传null为默认exchange
	 * @param routingKey 路由key
	 * @param data 发送数据
	 */
	public void sendMessage(String id, String exchange, String routingKey, Object data) {
		// 发送到RabbitMQ
		if (StringUtils.isBlank(routingKey)) {
			return;
		}
		JSONObject dataJson = JSONObject.parseObject(JSON.toJSONString(data));
		JSONObject sendMsg = new JSONObject();
		sendMsg.put("id", id);
		// 路由key
		sendMsg.put("routingKey", routingKey);
		sendMsg.put("send_time", System.currentTimeMillis());
		sendMsg.put("data", dataJson);

		rabbitMqSender.send(exchange, routingKey, sendMsg.toJSONString());
		log.info("RabbitMQ exchanger:{}, routingKey:{}, message:{} send succeed",
				exchange, routingKey, sendMsg);
	}
}
