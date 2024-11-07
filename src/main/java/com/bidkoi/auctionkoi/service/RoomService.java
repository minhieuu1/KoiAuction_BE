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

import java.time.LocalDateTime;
import java.util.Date;
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
        boolean existKoi = iroomRepo.existsByKoi_KoiId(koiId);
        if(existKoi) {
            throw new AppException(ErrorCode.KOI_EXISTED);
        }
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

                .orElseThrow(()-> new AppException(ErrorCode.AUCTION_ID_NOT_FOUND));

        return iroomRepo.findByAuctionId(auctionId);
    }

    @Override
    public void resetTimeRoom(Long roomId) {
        Room room = iroomRepo.findById(roomId)
                .orElseThrow(()-> new AppException(ErrorCode.ROOM_NOT_FOUND));

        Auction auction = iauctionRepo.findByAuctionId(room.getAuctionId());

        LocalDateTime newEndTime = LocalDateTime.now().plusMinutes(30);

        if(newEndTime.isBefore(auction.getEndTime()) ||  newEndTime.isEqual(auction.getEndTime())) {
            room.setEndTime(newEndTime);
        }else{
            room.setEndTime(auction.getEndTime());
        }

        room.setEndTime(LocalDateTime.now().plusMinutes(30));
        iroomRepo.save(room);
    }


}
