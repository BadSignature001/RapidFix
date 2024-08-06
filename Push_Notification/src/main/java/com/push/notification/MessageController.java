package com.push.notification;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@org.springframework.stereotype.Controller
public class MessageController {

	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/application")
	@SendTo("/all/messages")
	public Message send(final Message message) throws Exception {
		return message;
	}
	
	@MessageMapping("/private")
    public void sendToSpecificUser(@Payload CustomMessage message) {
        simpMessagingTemplate.convertAndSendToUser(message.getTo(), "/specific", message);
    }
}
