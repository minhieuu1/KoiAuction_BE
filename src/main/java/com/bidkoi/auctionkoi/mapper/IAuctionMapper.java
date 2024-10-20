package com.bidkoi.auctionkoi.mapper;

import com.bidkoi.auctionkoi.dto.AuctionDTO;
import com.bidkoi.auctionkoi.payload.request.KoiRequest;
import com.bidkoi.auctionkoi.payload.request.UpdateAuctionRequest;
import com.bidkoi.auctionkoi.pojo.Auction;
import com.bidkoi.auctionkoi.pojo.Koi;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IAuctionMapper {
    AuctionDTO toAuctionDTO(Auction auction);
    Auction toAuction(AuctionDTO auctionDTO);

    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    void updateAuction(@MappingTarget Auction auction, UpdateAuctionRequest request);
}
