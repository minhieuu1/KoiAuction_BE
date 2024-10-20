package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.payload.request.PlaceBidRequest;
import com.bidkoi.auctionkoi.pojo.Bid;
import java.util.List;

public interface IBidService {
    Bid createBid(String bidderID, Long RoomID);

    Bid placeBid(String bidderID, Long roomID, PlaceBidRequest request);

    Bid getWinningBid(Long roomID);

    Bid placeMutipleBid(String bidderID, Long roomID, PlaceBidRequest request);
}
