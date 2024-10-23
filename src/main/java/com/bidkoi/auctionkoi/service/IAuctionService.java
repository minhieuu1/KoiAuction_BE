package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.AuctionDTO;
import com.bidkoi.auctionkoi.dto.RoomDTO;
import com.bidkoi.auctionkoi.payload.request.UpdateAuctionRequest;
import com.bidkoi.auctionkoi.pojo.Auction;

import java.util.List;

public interface IAuctionService {
    AuctionDTO createAuction(AuctionDTO auctionDTO);
    RoomDTO addRoomToAuction(Long auctionId, Long roomId);
    List<Auction> getAll();


    public AuctionDTO updateAuction(Long auctionId, UpdateAuctionRequest request);

    public void deleteAuction(Long auctionId);

    AuctionDTO getAuctionById(Long auctionId);
    AuctionDTO updateStatus(Long auctionId);
    AuctionDTO getAuctionActive();
}
