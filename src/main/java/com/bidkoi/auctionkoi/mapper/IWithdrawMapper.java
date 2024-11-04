package com.bidkoi.auctionkoi.mapper;

import com.bidkoi.auctionkoi.dto.WithdrawDTO;
import com.bidkoi.auctionkoi.pojo.Withdraw;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IWithdrawMapper {

    WithdrawDTO toWithdrawDTO(Withdraw withdraw);
}
