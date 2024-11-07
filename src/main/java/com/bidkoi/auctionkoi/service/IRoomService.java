package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.RoomDTO;
import com.bidkoi.auctionkoi.pojo.Room;

import java.util.List;

public interface IRoomService {

    void deleteRoom(Long roomId);
    RoomDTO createRoom(Long koiId);
    List<Room> getAllRooms();
    List<Room> getRoomInAuction(Long auctionId);
    void resetTimeRoom(Long roomId);
}
