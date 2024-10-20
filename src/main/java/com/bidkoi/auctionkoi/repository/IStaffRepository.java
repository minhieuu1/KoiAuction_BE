package com.bidkoi.auctionkoi.repository;

import com.bidkoi.auctionkoi.dto.StaffDTO;
import com.bidkoi.auctionkoi.pojo.Account;
import com.bidkoi.auctionkoi.pojo.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IStaffRepository extends JpaRepository<Staff, Long> {
    Staff findByAccount(Account account);
//    StaffDTO findByAccount(Account account);
}
