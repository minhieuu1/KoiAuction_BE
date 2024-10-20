package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.payload.request.PlaceBidRequest;
import com.bidkoi.auctionkoi.payload.response.ApiResponse;
import com.bidkoi.auctionkoi.pojo.Bid;
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

    @PostMapping("/join/{bidderId}/{roomId}")
    ApiResponse<Bid> oneBidTime(@PathVariable String bidderId, @PathVariable Long roomId, @RequestBody PlaceBidRequest request){
        return ApiResponse.<Bid>builder()
                .data(service.placeBid(bidderId, roomId, request))
                .build();
    }


    @GetMapping("/winning/{roomId}")
    public ApiResponse<Bid> getWinningBid(@PathVariable Long roomId) {
        return ApiResponse.<Bid>builder()
                .data(service.getWinningBid(roomId))
                .build();
    }
}
