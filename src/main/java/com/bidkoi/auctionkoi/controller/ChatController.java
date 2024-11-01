package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.dto.MessageDTO;
import com.bidkoi.auctionkoi.dto.PlaceBid;
import com.bidkoi.auctionkoi.mapper.IMessageMapper;
import com.bidkoi.auctionkoi.service.IMessageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@SecurityRequirement(name = "api")
@RequestMapping("/chat")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatController {

    SimpMessagingTemplate messagingTemplate;
    IMessageService service;



    @MessageMapping("/message/{roomId}") // Đường dẫn tương ứng /app/message/{roomId}
    public MessageDTO sendMessage(@DestinationVariable Long roomId, @Payload MessageDTO chatMessage) {
        service.save(chatMessage,roomId);
        messagingTemplate.convertAndSend("/room/" + roomId, chatMessage);
        return chatMessage;
    }

    @GetMapping("/{roomId}")
    ResponseEntity<List<MessageDTO>> getBid(@PathVariable Long roomId) {
        return ResponseEntity.ok(service.getMessage(roomId));
    }

//    @GetMapping("/{roomId}")
//    ResponseEntity<List<PlaceBid>> getBid(@PathVariable Long roomId) {
//        return ResponseEntity.ok(service.getBids(roomId));
//    }
}