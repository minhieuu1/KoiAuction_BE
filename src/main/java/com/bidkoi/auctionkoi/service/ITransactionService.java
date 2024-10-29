package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.pojo.Bid;

import java.util.List;

public interface ITransactionService {
    void rollBack(Long roomId);
    void rollbackToWinner(String bidderId,Long koiId);
    void rollbackToBreeder(Long koiId);
}
