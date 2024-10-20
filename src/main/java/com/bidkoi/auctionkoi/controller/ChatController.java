package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message/{roomId}") // Đường dẫn tương ứng /app/message/{roomId}
    public Message sendMessage(@DestinationVariable String roomId, @Payload Message chatMessage) {
        messagingTemplate.convertAndSend("/room/" + roomId, chatMessage);
        return chatMessage;
    }



//    @MessageMapping("/place")
//    @SendTo("/topic/bid")
//    public PlaceBid bid(@Payload PlaceBid placeBid){
//        return placeBid;
//    }
}