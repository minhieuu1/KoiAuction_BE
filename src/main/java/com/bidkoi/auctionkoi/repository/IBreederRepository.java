package com.bidkoi.auctionkoi.repository;

import com.bidkoi.auctionkoi.pojo.Account;
import com.bidkoi.auctionkoi.pojo.Breeder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IBreederRepository extends JpaRepository<Breeder, Long> {
    Breeder findBreederByAccount(Account account);
//    Breeder findBreederByAccountId(String accountId);
}
