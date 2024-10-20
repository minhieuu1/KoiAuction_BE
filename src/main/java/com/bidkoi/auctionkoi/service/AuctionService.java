package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.AuctionDTO;
import com.bidkoi.auctionkoi.dto.RoomDTO;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.mapper.IAuctionMapper;
import com.bidkoi.auctionkoi.mapper.IRoomMapper;
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
        Auction auction = iAuctionMapper.toAuction(auctionDTO);
        auction.setStatus(AuctionStatus.PENDING);
        return iAuctionMapper.toAuctionDTO(iAuctionRepository.save(auction));
    }

    @Override
    public List<Auction> getAll() {
        return iAuctionRepository.findAll();
    }

    @Override
    public AuctionDTO updateStatus(Long auctionId) {
        Auction auction = iAuctionRepository.findById(auctionId)
                .orElseThrow(()-> new AppException(ErrorCode.AUCTION_ID_NOT_FOUND));
        auction.setStatus(AuctionStatus.ACTIVE);
        return iAuctionMapper.toAuctionDTO(iAuctionRepository.save(auction));
    }

    @Override
    public AuctionDTO getAuctionActive() {
        Auction auction = iAuctionRepository.findAuctionByStatus(AuctionStatus.ACTIVE);
        return iAuctionMapper.toAuctionDTO(auction);
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
