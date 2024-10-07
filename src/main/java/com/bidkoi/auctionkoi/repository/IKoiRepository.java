package com.bidkoi.auctionkoi.repository;


import com.bidkoi.auctionkoi.pojo.Breeder;
import com.bidkoi.auctionkoi.pojo.Koi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IKoiRepository extends JpaRepository<Koi,String> {
    boolean existsByKoiId(String koiId);
    List<Koi> findByBreeder(Breeder breeder);
}
