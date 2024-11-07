package com.bidkoi.auctionkoi.repository;

import com.bidkoi.auctionkoi.pojo.Account;
import com.bidkoi.auctionkoi.pojo.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findWalletByAccount(Account account);
    Wallet findWalletsByAccount_Id(String accountId);
}
