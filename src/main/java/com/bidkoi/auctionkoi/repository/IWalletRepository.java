package com.bidkoi.auctionkoi.repository;

import com.bidkoi.auctionkoi.pojo.Account;
import com.bidkoi.auctionkoi.pojo.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findWalletByAccount(Account account);
}
