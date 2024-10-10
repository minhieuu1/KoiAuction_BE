package com.bidkoi.auctionkoi.mapper;

import com.bidkoi.auctionkoi.dto.WalletDTO;
import com.bidkoi.auctionkoi.payload.request.WalletRequest;
import com.bidkoi.auctionkoi.pojo.Wallet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IWalletMapper {
    Wallet toWallet(WalletRequest request);
    WalletDTO toWalletDTO(Wallet wallet);
}
