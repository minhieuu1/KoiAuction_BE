package com.bidkoi.auctionkoi.mapper;

import com.bidkoi.auctionkoi.dto.BreederDTO;
import com.bidkoi.auctionkoi.dto.StaffDTO;
import com.bidkoi.auctionkoi.payload.request.BreederRequest;
import com.bidkoi.auctionkoi.payload.request.StaffRequest;
import com.bidkoi.auctionkoi.pojo.Breeder;
import com.bidkoi.auctionkoi.pojo.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface IStaffMapper {
    StaffDTO toStaffDTO(Staff staff);
    void updateStaff(@MappingTarget Staff staff, StaffRequest request);
}
