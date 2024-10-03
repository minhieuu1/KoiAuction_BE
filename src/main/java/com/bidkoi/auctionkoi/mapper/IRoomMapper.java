package com.bidkoi.auctionkoi.mapper;

import com.bidkoi.auctionkoi.dto.RoomDTO;
import com.bidkoi.auctionkoi.pojo.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IRoomMapper {
    RoomDTO toRoomDTO(Room room);
}
