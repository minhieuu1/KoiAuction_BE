package com.bidkoi.auctionkoi.repository;

import com.bidkoi.auctionkoi.pojo.Account;
import com.bidkoi.auctionkoi.pojo.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IWithdrawRepository extends JpaRepository<Withdraw, Long> {

    Optional<Withdraw> findWithdrawByAccount(Account account);
}
