package com.bidkoi.auctionkoi.repository;

import com.bidkoi.auctionkoi.pojo.Bid;
import com.bidkoi.auctionkoi.pojo.Bidder;
import com.bidkoi.auctionkoi.pojo.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBidRepository extends JpaRepository<Bid,Long> {
    List<Bid> findByBidder(Bidder bidder);
    List<Bid> findByRoom(Room room);

    List<Bid> findByUsernameIsNotAndRoom(String winner,Room room);
//    List<Bid> findByUsernameIsNotOrUsernameIsNull(String winner);

    Bid findByBidderAndRoom(Bidder bidder, Room room);
    boolean existsByBidderAndRoom(Bidder bidder, Room room);



}
