package com.bidkoi.auctionkoi.repository;

import com.bidkoi.auctionkoi.pojo.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ITransactionsRepository extends JpaRepository<Transactions, UUID> {

}
