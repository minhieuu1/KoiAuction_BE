package com.bidkoi.auctionkoi.repository;

import com.bidkoi.auctionkoi.pojo.Bidder;
import com.bidkoi.auctionkoi.pojo.Breeder;
import com.bidkoi.auctionkoi.pojo.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShippingRepository extends JpaRepository<Shipping, Long> {
    boolean existsByBreederAndBidder(Breeder breeder, Bidder bidder);
}
