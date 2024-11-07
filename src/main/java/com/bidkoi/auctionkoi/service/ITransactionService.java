package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.TransactionsDTO;
import com.bidkoi.auctionkoi.dto.WalletDTO;
import com.bidkoi.auctionkoi.pojo.Bid;
import com.bidkoi.auctionkoi.pojo.Transactions;
import com.bidkoi.auctionkoi.pojo.Wallet;

import java.util.List;

public interface ITransactionService {

//    List<Bid> rollBack(Long roomId);

    public void rollbackToBreeder(Long koiId);

    void rollBack(Long auctionId);
    void rollbackToWinner(String bidderId,Long koiId);
    void transferToBreeder(Long roomId);
    
    List<TransactionsDTO> getTransactions(String accountId);
    List<TransactionsDTO> getAllTransactions();
}
