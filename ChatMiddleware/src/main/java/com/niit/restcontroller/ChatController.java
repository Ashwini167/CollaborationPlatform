package com.niit.restcontroller;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import com.niit.model.Message;
import com.niit.model.OutputMessage;

@RestController
public class ChatController {
	private static final Logger log = LoggerFactory.getLogger(ChatController.class);
	
	@MessageMapping("/chat")
	@SendTo("/topic/message")
	public OutputMessage sendMessage(Message message) {
		log.info("Inside Send Message Method of Chat Controller");
		return new OutputMessage(message, new Date());
	}
}