package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.AuctionDTO;
import com.bidkoi.auctionkoi.dto.RoomDTO;
import com.bidkoi.auctionkoi.pojo.Auction;

import java.util.List;

public interface IAuctionService {
    AuctionDTO createAuction(AuctionDTO auctionDTO);
    RoomDTO addRoomToAuction(Long auctionId, Long roomId);
    List<Auction> getAll();

    AuctionDTO updateStatus(Long auctionId);
    AuctionDTO getAuctionActive();
}
