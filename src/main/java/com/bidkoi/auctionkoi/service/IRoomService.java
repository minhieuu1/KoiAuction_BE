package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.RoomDTO;
import com.bidkoi.auctionkoi.pojo.Room;

import java.util.List;

public interface IRoomService {
    RoomDTO createRoom(Long koiId);
    List<Room> getAllRooms();
    List<Room> getRoomInAuction(Long auctionId);
}
