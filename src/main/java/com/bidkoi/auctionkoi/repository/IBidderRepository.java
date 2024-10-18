package com.bidkoi.auctionkoi.repository;


import com.bidkoi.auctionkoi.pojo.Account;
import com.bidkoi.auctionkoi.pojo.Bidder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBidderRepository extends JpaRepository<Bidder, String> {
    Optional<Bidder> findByAccountId(String accountId);
    Bidder findBidderByAccount(Account account);
}
