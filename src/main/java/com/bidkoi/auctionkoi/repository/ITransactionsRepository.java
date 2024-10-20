package com.bidkoi.auctionkoi.repository;

import com.bidkoi.auctionkoi.pojo.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ITransactionsRepository extends JpaRepository<Transactions, UUID> {

}
