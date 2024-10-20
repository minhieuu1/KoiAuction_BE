package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.WalletDTO;
import com.bidkoi.auctionkoi.payload.request.WalletRequest;
import java.util.UUID;

public interface IWalletService {
    WalletDTO createWallet(WalletRequest request, String accountId);

    String createUrl(WalletRequest walletRequest, String accountId) throws Exception;

    void handleVnPayCallback(UUID transactionId, String responseCode, double vnpAmount);

    WalletDTO getWallet(String accountId);
}
