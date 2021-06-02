package com.lab4.demo.websocket;


import com.lab4.demo.websocket.dto.Message;
import com.lab4.demo.websocket.dto.ResponseMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

import static com.lab4.demo.UrlMapping.COURSES;
import static com.lab4.demo.UrlMapping.STUDENTS;

@Controller

public class MessageController {

    @MessageMapping("/mes")
    @SendTo("/connect/mes")
    public ResponseMessage getMessage(Message message){
        return new ResponseMessage(message.getMessageContent());

    }
}
