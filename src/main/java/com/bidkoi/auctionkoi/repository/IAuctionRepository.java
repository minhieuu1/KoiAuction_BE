package com.bidkoi.auctionkoi.repository;

import com.bidkoi.auctionkoi.pojo.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuctionRepository extends JpaRepository<Auction,Long> {
    boolean existsByAuctionId(Long auctionId);
}
