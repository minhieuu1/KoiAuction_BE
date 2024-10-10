package com.bidkoi.auctionkoi.mapper;

import com.bidkoi.auctionkoi.dto.BreederDTO;
import com.bidkoi.auctionkoi.payload.request.BreederRequest;
import com.bidkoi.auctionkoi.pojo.Breeder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface IBreederMapper {
    BreederDTO toBreederDTO(Breeder breeder);
    void updateBreeder(@MappingTarget Breeder breeder, BreederRequest request);
}
