package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.WalletDTO;
import com.bidkoi.auctionkoi.payload.request.WalletRequest;
import com.bidkoi.auctionkoi.pojo.Wallet;

public interface IWalletService {
    WalletDTO createWallet(WalletRequest request,String accountId);
}
