package com.bidkoi.auctionkoi.repository;

import com.bidkoi.auctionkoi.pojo.Transactions;
import com.bidkoi.auctionkoi.pojo.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ITransactionsRepository extends JpaRepository<Transactions, String> {
    List<Transactions> findByWallet(Wallet wallet);
}
