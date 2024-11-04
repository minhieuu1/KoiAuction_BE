package com.bidkoi.auctionkoi.service;


import com.bidkoi.auctionkoi.dto.WithdrawDTO;
import com.bidkoi.auctionkoi.payload.request.WithdrawRequest;

public interface IWithdrawService {
    WithdrawDTO createWithdraw(WithdrawRequest request, String accountId);

    WithdrawDTO approveWithdraw(Long withdrawId, Long staffId);

    WithdrawDTO rejectWithdraw(Long withdrawId, Long staffId);
}
