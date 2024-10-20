package com.bidkoi.auctionkoi.repository;

import com.bidkoi.auctionkoi.enums.AuctionStatus;
import com.bidkoi.auctionkoi.pojo.Auction;
import com.bidkoi.auctionkoi.pojo.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAuctionRepository extends JpaRepository<Auction,Long> {
    boolean existsByAuctionId(Long auctionId);

    List<Room> findByAuctionId(Long auctionId);
    Auction findAuctionByStatus(AuctionStatus status);

}
