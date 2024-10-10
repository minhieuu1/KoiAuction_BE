package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.StaffDTO;
import com.bidkoi.auctionkoi.payload.request.StaffRequest;

public interface IStaffService {
    StaffDTO updateStaff(String accountId1,StaffRequest staffDTO);
}
