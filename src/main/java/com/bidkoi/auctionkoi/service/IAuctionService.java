package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.AuctionDTO;
import com.bidkoi.auctionkoi.dto.RoomDTO;
import com.bidkoi.auctionkoi.pojo.Auction;

import java.util.List;

public interface IAuctionService {
    public AuctionDTO createAuction(AuctionDTO auctionDTO);
    public RoomDTO addRoomToAuction(Long auctionId, Long roomId);

    public List<Auction> getAll();
}
