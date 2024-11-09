package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.dto.AccountDTO;
import com.bidkoi.auctionkoi.dto.RoomDTO;
import com.bidkoi.auctionkoi.payload.response.ApiResponse;
import com.bidkoi.auctionkoi.pojo.Room;
import com.bidkoi.auctionkoi.service.IRoomService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
@CrossOrigin("*")
@SecurityRequirement(name = "api")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomController {
    IRoomService roomService;

    @PostMapping("/creation/{koiId}")
    ResponseEntity<ApiResponse<RoomDTO>> createRoom(@PathVariable Long koiId) {
        ApiResponse<RoomDTO> response = ApiResponse.<RoomDTO>builder().data(roomService.createRoom(koiId)).build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


//    @DeleteMapping("/delete/{roomId}")
//    String deleteRoom(@PathVariable Long roomId) {
//        roomService.deleteRoom(roomId);
//        return "Room deleted successfully!!!";
//
//    }

    @GetMapping
    ApiResponse<List<Room>> getAllRooms() {
        return ApiResponse.<List<Room>>builder().data(roomService.getAllRooms()).build();
    }

    @GetMapping("/{auctionId}")
    ApiResponse<List<Room>> getRoomInAuction(@PathVariable Long auctionId) {
        return ApiResponse.<List<Room>>builder().data(roomService.getRoomInAuction(auctionId)).build();

    }

    @PatchMapping("/reset/{roomId}")
    ResponseEntity<Void> resetRoom(@PathVariable Long roomId) {
        roomService.resetTimeRoom(roomId);
        return ResponseEntity.noContent().build();
    }
}
