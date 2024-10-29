package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.RoomDTO;
import com.bidkoi.auctionkoi.enums.AuctionStatus;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.mapper.IRoomMapper;

import com.bidkoi.auctionkoi.pojo.Auction;
import com.bidkoi.auctionkoi.pojo.Room;
import com.bidkoi.auctionkoi.repository.IAuctionRepository;
import com.bidkoi.auctionkoi.repository.IKoiRepository;
import com.bidkoi.auctionkoi.repository.IRoomRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomService implements IRoomService {

    IRoomRepository iroomRepo;
    IRoomMapper roomMapper;
    IKoiRepository ikoiRepo;
    IAuctionRepository iauctionRepo;

    @Override
    public RoomDTO createRoom(Long koiId) {
        Room room = new Room();
        room.setKoi(ikoiRepo.findById(koiId).
                orElseThrow(()->new AppException(ErrorCode.KOI_NOT_FOUND)));

//        room.setType(roomDTO.getType());
//        room.setStartTime(roomDTO.getStartTime());
//        room.setEndTime(roomDTO.getEndTime());
//        room.setStatus(AuctionStatus.PENDING);
        return roomMapper.toRoomDTO(iroomRepo.save(room));
    }

    @Override
    public List<Room> getAllRooms() {
        return iroomRepo.findAll();
    }

    @Override

    public void deleteRoom(Long roomId) {
        iroomRepo.deleteById(roomId);
    }
    public List<Room> getRoomInAuction(Long auctionId) {
        Auction auction = iauctionRepo.findById(auctionId)
                .orElseThrow(() -> new AppException(ErrorCode.AUCTION_ID_NOT_FOUND));
        return iroomRepo.findByAuctionId(auctionId);
    }


}
