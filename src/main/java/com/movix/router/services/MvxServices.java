package com.movix.router.services;

import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.movix.router.dto.MvxBean;

@Service
public class MvxServices {
	
	public static void example(MvxBean bodyIn) {
		bodyIn.setId(bodyIn.getId() * 10);
	}
	
	public static Message example2(MvxBean bodyIn) {
		MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("queue-transaction-id", UUID.randomUUID().toString());
        Gson gson = new Gson();
        return new Message(gson.toJson(bodyIn).getBytes(), messageProperties);
	}
}
