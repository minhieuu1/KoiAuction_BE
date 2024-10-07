package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.pojo.Bid;
import java.util.List;

public interface IBidService {
    Bid createBid(String bidderID, Long RoomID);
}
