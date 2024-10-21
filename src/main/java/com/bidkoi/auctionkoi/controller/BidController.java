package com.bidkoi.auctionkoi.controller;


import com.bidkoi.auctionkoi.dto.BidDTO;
import com.bidkoi.auctionkoi.dto.PlaceBid;
import com.bidkoi.auctionkoi.payload.response.ApiResponse;
import com.bidkoi.auctionkoi.payload.response.Winner;
import com.bidkoi.auctionkoi.pojo.Bid;
import com.bidkoi.auctionkoi.pojo.Bidder;
import com.bidkoi.auctionkoi.service.IBidService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/placeBid")
@RequiredArgsConstructor
@CrossOrigin("*")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BidController {
    IBidService service;
    SimpMessagingTemplate messagingTemplate;

    @PostMapping("/creation/{bidderId}/{roomId}")
    ApiResponse<Bid> create(@PathVariable String bidderId, @PathVariable Long roomId) {
        return ApiResponse.<Bid>builder()
                .data(service.registerBid(bidderId,roomId))
                .build();
    }

    @GetMapping("/{bidderId}/{roomId}")
    ResponseEntity<Boolean> existInRoom(@PathVariable String bidderId, @PathVariable Long roomId) {
        return ResponseEntity.ok(service.joinBids(bidderId,roomId));
    }



    @MessageMapping("/bid/{roomId}")
    public ResponseEntity<String> sendBid(@DestinationVariable Long roomId, @Payload PlaceBid bid) {
        bid = service.updateBid(roomId, bid);
        messagingTemplate.convertAndSend("/bid/"+roomId,bid);
        return ResponseEntity.ok("done");
    }

    @GetMapping("/{roomId}")
    ResponseEntity<List<PlaceBid>> getBid(@PathVariable Long roomId) {
        return ResponseEntity.ok(service.getBids(roomId));
    }

    @GetMapping("/winner/{roomId}")
    public ApiResponse<Winner> getWinner(@PathVariable Long roomId) {
        return  ApiResponse.<Winner>builder()
                .data(service.getWinner(roomId))
                .build();
    }

//    @PostMapping("/bidding/{bidderId}/{roomId}")
//    ApiResponse<Bid> bidding(@PathVariable Long roomId, @PathVariable String bidderId, @RequestBody double price) {
//        return ApiResponse.<Bid>builder()
//                .data(service.bidding(bidderId,roomId,price))
//                .build();
//    }


}
