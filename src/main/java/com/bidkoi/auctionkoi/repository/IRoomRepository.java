package com.bidkoi.auctionkoi.repository;

import com.bidkoi.auctionkoi.pojo.Koi;
import com.bidkoi.auctionkoi.pojo.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByAuctionId(Long auctionId);

}
