package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.payload.response.ApiResponse;
import com.bidkoi.auctionkoi.pojo.Bid;
import com.bidkoi.auctionkoi.pojo.Bidder;
import com.bidkoi.auctionkoi.service.IBidService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bid")
@RequiredArgsConstructor
@CrossOrigin
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BidController {
    IBidService service;

    @PostMapping("/create/{bidderId}/{roomId}")
    ApiResponse<Bid> create(@PathVariable String bidderId, @PathVariable Long roomId) {
        return ApiResponse.<Bid>builder()
                .data(service.createBid(bidderId,roomId))
                .build();
    }



//    @PostMapping("/bidding/{bidderId}/{roomId}")
//    ApiResponse<Bid> bidding(@PathVariable Long roomId, @PathVariable String bidderId, @RequestBody double price) {
//        return ApiResponse.<Bid>builder()
//                .data(service.bidding(bidderId,roomId,price))
//                .build();
//    }


}