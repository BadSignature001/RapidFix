package com.spring.rapidfix.controller;



import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class NotificationController {

    @MessageMapping("/sendNotification")
    @SendTo("/topic/notifications")
    public String sendNotification(String message) {
        return HtmlUtils.htmlEscape(message);
    }
}

//s