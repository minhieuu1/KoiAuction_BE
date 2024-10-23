package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.dto.RoomDTO;
import com.bidkoi.auctionkoi.payload.response.ApiResponse;
import com.bidkoi.auctionkoi.pojo.Room;
import com.bidkoi.auctionkoi.service.IRoomService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
@CrossOrigin("*")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomController {
    IRoomService roomService;

    @PostMapping("/creation/{koiId}")
    ApiResponse<RoomDTO> createRoom(@PathVariable Long koiId) {
        return ApiResponse.<RoomDTO>builder()
                .data(roomService.createRoom(koiId))
                .build();
    }


    @DeleteMapping("/delete/{roomId}")
    String deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return "Room deleted successfully!!!";

    }
    
    @GetMapping
    ApiResponse<List<Room>> getAllRooms() {
        return ApiResponse.<List<Room>>builder().data(roomService.getAllRooms()).build();
    }

    @GetMapping("/{auctionId}")
    ApiResponse<List<Room>> getRoomInAuction(@PathVariable Long auctionId) {
        return ApiResponse.<List<Room>>builder().data(roomService.getRoomInAuction(auctionId)).build();

    }
}
