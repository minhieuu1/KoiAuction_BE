package com.bidkoi.auctionkoi.mapper;

import com.bidkoi.auctionkoi.dto.BidDTO;
import com.bidkoi.auctionkoi.dto.PlaceBid;
import com.bidkoi.auctionkoi.pojo.Bid;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface IBidMapper {

    List<PlaceBid> toPlaceBids(List<Bid> bids);
}
