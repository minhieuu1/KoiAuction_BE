package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.AuctionDTO;
import com.bidkoi.auctionkoi.dto.RoomDTO;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.mapper.IAuctionMapper;
import com.bidkoi.auctionkoi.mapper.IRoomMapper;
import com.bidkoi.auctionkoi.payload.request.UpdateAuctionRequest;
import com.bidkoi.auctionkoi.pojo.Auction;
import com.bidkoi.auctionkoi.enums.AuctionStatus;
import com.bidkoi.auctionkoi.pojo.Room;
import com.bidkoi.auctionkoi.repository.IAuctionRepository;
import com.bidkoi.auctionkoi.repository.IRoomRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuctionService implements IAuctionService {
    IAuctionRepository iAuctionRepository;
    IAuctionMapper iAuctionMapper;
    IRoomRepository iRoomRepository;
    IRoomMapper iRoomMapper;



    @Override
    @Transactional
    public AuctionDTO createAuction(AuctionDTO auctionDTO) {

        List<Auction> overlappingAuctions = iAuctionRepository.findOverlappingAuctions(auctionDTO.getStartTime(), auctionDTO.getEndTime());
        if (!overlappingAuctions.isEmpty()) {
            throw new AppException(ErrorCode.AUCTION_SAME_TIME);
        }

        Auction auction = iAuctionMapper.toAuction(auctionDTO);

        LocalDateTime today = LocalDateTime.now();
        if(auction.getStartTime().isBefore(today)){
            throw new AppException(ErrorCode.INVALID_AUCTION_DATE);
        }
        else if (auction.getEndTime().isBefore(auction.getStartTime())){
            throw new AppException(ErrorCode.INVALID_AUCTION_END_DATE);
        }
        
        auction.setStatus(AuctionStatus.PENDING);

        return iAuctionMapper.toAuctionDTO(iAuctionRepository.save(auction));
    }

    //View List Auction
    @Override
    public List<Auction> getAll() {
        return iAuctionRepository.findAll();
    }

    //Update Auction
    @Override
    @Transactional
    public AuctionDTO updateAuction(Long auctionId, UpdateAuctionRequest request) {

        List<Auction> overlappingAuctions = iAuctionRepository.findOverlappingAuctionsIsNotAuctionId(request.getStartTime(), request.getEndTime(),auctionId);
        if (!overlappingAuctions.isEmpty()) {
            throw new AppException(ErrorCode.AUCTION_SAME_TIME);
        }
        Auction auction = iAuctionRepository.findById(auctionId)
                .orElseThrow(() -> new AppException(ErrorCode.AUCTION_ID_NOT_FOUND));

        LocalDateTime today = LocalDateTime.now();
        if(request.getStartTime().isBefore(today)){
            throw new AppException(ErrorCode.INVALID_AUCTION_DATE);
        }
        else if (request.getEndTime().isBefore(request.getStartTime())){
            throw new AppException(ErrorCode.INVALID_AUCTION_END_DATE);
        }

//        auction.setStartTime(request.getStartTime());
//        auction.setEndTime(request.getEndTime());
        iAuctionMapper.updateAuction(auction, request);
        System.out.println("Updated Auction StartTime: " + auction.getStartTime());
        System.out.println("Updated Auction EndTime: " + auction.getEndTime());
        return iAuctionMapper.toAuctionDTO(iAuctionRepository.save(auction));
    }

    //Delete Auction
    @Override
    public void deleteAuction(Long auctionId) {
        List<Room> rooms = iRoomRepository.findByAuctionId(auctionId);
        for(Room room : rooms){
            removeRoomFromAuction(room.getRoomId());
        }
        iAuctionRepository.deleteById(auctionId);
    }


    @Override
    public AuctionDTO getAuctionById(Long auctionId) {
        Auction auction = iAuctionRepository.findById(auctionId)
                .orElseThrow(()-> new AppException(ErrorCode.AUCTION_ID_NOT_FOUND));
        return iAuctionMapper.toAuctionDTO(auction);
    }



    //Add Room to Auction
    @Override
    public void activeAuction(Long auctionId) {


        Auction auction = iAuctionRepository.findById(auctionId)
                .orElseThrow(()-> new AppException(ErrorCode.AUCTION_ID_NOT_FOUND));
        boolean exist = iAuctionRepository.existsAuctionByStatus(AuctionStatus.ACTIVE);
        if(exist){
            throw new AppException(ErrorCode.HAS_AUCTION_ACTIVE);
        }
        List<Room> rooms = iRoomRepository.findByAuctionId(auctionId);
        for (Room room : rooms) {
            room.setEndTime(auction.getEndTime());
        }
        auction.setStatus(AuctionStatus.ACTIVE);
        iAuctionMapper.toAuctionDTO(iAuctionRepository.save(auction));
    }

    @Override
    public void closeAuction(Long auctionId) {
        Auction auction = iAuctionRepository.findById(auctionId)
                .orElseThrow(()-> new AppException(ErrorCode.AUCTION_ID_NOT_FOUND));
        auction.setStatus(AuctionStatus.CLOSED);
        iAuctionMapper.toAuctionDTO(iAuctionRepository.save(auction));
    }

    @Override
    public AuctionDTO getAuctionActive() {
        Auction auction = iAuctionRepository.findAuctionByStatus(AuctionStatus.ACTIVE);
        return iAuctionMapper.toAuctionDTO(auction);
    }

    @Override
    public void removeRoomFromAuction(Long roomId) {
        Room room = iRoomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));
        room.setAuctionId(null);
        iRoomRepository.save(room);
    }

    @Override
    public RoomDTO addRoomToAuction(Long auctionId, Long roomId) {
        Auction auction = iAuctionRepository.findById(auctionId)
                .orElseThrow(() -> new AppException(ErrorCode.AUCTION_ID_NOT_FOUND));

        Room room = iRoomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));

        room.setAuctionId(auction.getAuctionId());

        return iRoomMapper.toRoomDTO(iRoomRepository.save(room));
    }




}
