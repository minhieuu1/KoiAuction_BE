package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.pojo.Bid;

import java.util.List;

public interface ITransactionService {

//    List<Bid> rollBack(Long roomId);

    public void rollbackToBreeder(Long koiId);

    void rollBack(Long roomId);
    void rollbackToWinner(String bidderId,Long koiId);
    void transferToBreeder(Long roomId);
    

}
