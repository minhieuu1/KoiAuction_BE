package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.BidderDTO;
import com.bidkoi.auctionkoi.dto.PlaceBid;
import com.bidkoi.auctionkoi.payload.response.Winner;
import com.bidkoi.auctionkoi.pojo.Bid;
import com.bidkoi.auctionkoi.pojo.Bidder;

import java.util.List;

public interface IBidService {
    Bid createBid(String bidderID, Long roomID);
    PlaceBid updateBid(Long roomID, PlaceBid bid);
    Winner getWinner(Long roomID);
}
