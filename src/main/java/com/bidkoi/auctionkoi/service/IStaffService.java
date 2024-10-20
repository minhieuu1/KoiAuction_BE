package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.StaffDTO;
import com.bidkoi.auctionkoi.payload.request.StaffRequest;
import com.bidkoi.auctionkoi.pojo.Account;
import com.bidkoi.auctionkoi.pojo.Staff;

import java.util.Optional;

public interface IStaffService {
    StaffDTO updateStaff(String accountId1,StaffRequest staffDTO);
    StaffDTO getStaff(String accountId);
}
