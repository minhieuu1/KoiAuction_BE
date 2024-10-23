package com.bidkoi.auctionkoi.repository;

import com.bidkoi.auctionkoi.pojo.Bidder;
import com.bidkoi.auctionkoi.pojo.Breeder;
import com.bidkoi.auctionkoi.pojo.Koi;
import com.bidkoi.auctionkoi.pojo.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IShippingRepository extends JpaRepository<Shipping, Long> {
    boolean existsByKoiAndBidder(Koi koi, Bidder bidder);
    Shipping findByKoi(Koi koi);
    boolean existsByKoi(Koi koi);
    List<Shipping> findByBidder(Bidder bidder);
    List<Shipping> findByKoiIn(List<Koi> kois);
    List<Shipping> findByShippingId(Long shipping);
}
