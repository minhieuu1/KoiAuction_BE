package com.bidkoi.auctionkoi.mapper;

import com.bidkoi.auctionkoi.dto.AuctionDTO;
import com.bidkoi.auctionkoi.pojo.Auction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAuctionMapper {
    AuctionDTO toAuctionDTO(Auction auction);
    Auction toAuction(AuctionDTO auctionDTO);
}
