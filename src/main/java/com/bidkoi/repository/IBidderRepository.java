package com.bidkoi.repository;

import com.bidkoi.pojo.Bidder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBidderRepository extends JpaRepository<Bidder, String> {
    Optional<Bidder> findByAccountId(String accountId);

}
