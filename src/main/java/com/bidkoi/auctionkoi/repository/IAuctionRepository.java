package com.bidkoi.auctionkoi.repository;

import com.bidkoi.auctionkoi.enums.AuctionStatus;
import com.bidkoi.auctionkoi.pojo.Auction;
import com.bidkoi.auctionkoi.pojo.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IAuctionRepository extends JpaRepository<Auction,Long> {
    boolean existsByAuctionId(Long auctionId);
    Auction findByAuctionId(Long auctionId);

    Auction findAuctionByStatus(AuctionStatus status);
    boolean existsAuctionByStatus(AuctionStatus status);
    @Query("SELECT a FROM Auction a WHERE a.startTime <= :endTime AND a.endTime >= :startTime")
    List<Auction> findOverlappingAuctions(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
    @Query("SELECT a FROM Auction a WHERE a.startTime <= :endTime AND a.endTime >= :startTime AND a.auctionId != :excludedAuctionId")
    List<Auction> findOverlappingAuctionsIsNotAuctionId(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("excludedAuctionId") Long excludedAuctionId
    );

}

