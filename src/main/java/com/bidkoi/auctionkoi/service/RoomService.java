package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.RoomDTO;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.exception.ErrorCode;
import com.bidkoi.auctionkoi.mapper.IRoomMapper;
import com.bidkoi.auctionkoi.pojo.Room;
import com.bidkoi.auctionkoi.repository.IKoiRepository;
import com.bidkoi.auctionkoi.repository.IRoomRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomService implements IRoomService {

    IRoomRepository iroomRepo;
    IRoomMapper roomMapper;
    IKoiRepository ikoiRepo;

    @Override
    public RoomDTO createRoom(Long koiId) {
        Room room = new Room();
        room.setKoi(ikoiRepo.findById(koiId).
                orElseThrow(()->new AppException(ErrorCode.KOI_NOT_FOUND)));
        return roomMapper.toRoomDTO(iroomRepo.save(room));
//        return null;
    }




}
