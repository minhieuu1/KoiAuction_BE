package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.pojo.Bid;

public interface IBidService {
    Bid createBid(String bidderID, Long RoomID);
}
