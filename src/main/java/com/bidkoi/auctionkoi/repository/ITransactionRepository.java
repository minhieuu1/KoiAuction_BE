package com.bidkoi.auctionkoi.repository;

import com.bidkoi.auctionkoi.pojo.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionRepository extends JpaRepository<Transactions, Long> {
}
