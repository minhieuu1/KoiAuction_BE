package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.dto.AuctionDTO;
import com.bidkoi.auctionkoi.dto.RoomDTO;
import com.bidkoi.auctionkoi.payload.request.UpdateAuctionRequest;
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
@RequestMapping("/auction")
@RequiredArgsConstructor
@CrossOrigin("*")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuctionController {
    IAuctionService iAuctionService;

    @PostMapping("/creation")
    public ResponseEntity<AuctionDTO> createAuction(@RequestBody AuctionDTO auction){
        return ResponseEntity.ok(iAuctionService.createAuction(auction));
    }

    @GetMapping
    public List<Auction> getAllAuction(){
        return iAuctionService.getAll();
    }

    @PostMapping("/{auctionId}/room/{roomId}")
    public ApiResponse<RoomDTO> addRoomToAuction(@PathVariable Long auctionId, @PathVariable Long roomId) {
        return ApiResponse.<RoomDTO>builder()
                .data(iAuctionService.addRoomToAuction(auctionId, roomId))
                .build();
    }


    @PutMapping("/update/{auctionId}")
    public ApiResponse<AuctionDTO> updateAuction(@PathVariable Long auctionId, @RequestBody UpdateAuctionRequest request){
        return ApiResponse.<AuctionDTO>builder()
                .data(iAuctionService.updateAuction(auctionId, request))
                .build();
    }

    @DeleteMapping("/delete/{auctionId}")
    String deleteAuction(@PathVariable Long auctionId){
        iAuctionService.deleteAuction(auctionId);
        return "Auction deleted successfully!";
    }

    

    @PutMapping("/{auctionId}/active")
    public ApiResponse<AuctionDTO> activateAuction(@PathVariable Long auctionId){
        return ApiResponse.<AuctionDTO>builder()
                .data(iAuctionService.updateStatus(auctionId))
                .build();
    }

    @GetMapping("/active")
    public ApiResponse<AuctionDTO> getAuctionActive(){
        return ApiResponse.<AuctionDTO>builder()
                .data(iAuctionService.getAuctionActive())
                .build();
    }

    @GetMapping("/{auctionId}")
    public ResponseEntity<AuctionDTO> getAuctionById(@PathVariable Long auctionId){
        return ResponseEntity.ok(iAuctionService.getAuctionById(auctionId));
    }
}
