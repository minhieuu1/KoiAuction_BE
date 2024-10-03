package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.dto.RoomDTO;
import com.bidkoi.auctionkoi.payload.response.ApiResponse;
import com.bidkoi.auctionkoi.service.IRoomService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
@CrossOrigin
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomController {
    IRoomService roomService;

    @PostMapping("/create/{koiId}")
    ApiResponse<RoomDTO> createRoom(@PathVariable String koiId) {
        return ApiResponse.<RoomDTO>builder()
                .data(roomService.createRoom(koiId))
                .build();
    }
}
