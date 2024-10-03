package com.bidkoi.auctionkoi.repository;


import com.bidkoi.auctionkoi.pojo.Koi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IKoiRepository extends JpaRepository<Koi,String> {
    boolean existsByKoiId(String koiId);
}
