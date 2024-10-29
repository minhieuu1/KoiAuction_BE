package com.bidkoi.auctionkoi.repository;


import com.bidkoi.auctionkoi.pojo.Breeder;
import com.bidkoi.auctionkoi.pojo.Koi;
import com.bidkoi.auctionkoi.pojo.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IKoiRepository extends JpaRepository<Koi,Long> {
    boolean existsByKoiId(Long koiId);
    List<Koi> findByBreeder(Breeder breeder);
}
