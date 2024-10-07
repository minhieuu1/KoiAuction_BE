package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.dto.AuctionDTO;
import com.bidkoi.auctionkoi.dto.RoomDTO;
import com.bidkoi.auctionkoi.payload.response.ApiResponse;
import com.bidkoi.auctionkoi.pojo.Auction;
import com.bidkoi.auctionkoi.service.IAuctionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auctions")
@RequiredArgsConstructor
@CrossOrigin
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuctionController {
    IAuctionService iAuctionService;

    @PostMapping("/create")
    public ResponseEntity<AuctionDTO> createAuction(@RequestBody AuctionDTO auction){
        return ResponseEntity.ok(iAuctionService.createAuction(auction));
    }

    @GetMapping
    public List<Auction> getAllAuction(){
        return iAuctionService.getAll();
    }

    @PostMapping("/{auctionId}/rooms/{roomId}")
    public ApiResponse<RoomDTO> addRoomToAuction(@PathVariable Long auctionId, @PathVariable Long roomId) {
        return ApiResponse.<RoomDTO>builder()
                .data(iAuctionService.addRoomToAuction(auctionId, roomId))
                .build();
    }

}
