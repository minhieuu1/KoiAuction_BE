package com.bidkoi.auctionkoi.repository;

import com.bidkoi.auctionkoi.pojo.Breeder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface IBreederRepository extends JpaRepository<Breeder, Long> {
}
