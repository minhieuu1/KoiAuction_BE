package com.bidkoi.auctionkoi.service;


import com.bidkoi.auctionkoi.dto.WithdrawDTO;
import com.bidkoi.auctionkoi.payload.request.RejectWithdrawRequest;
import com.bidkoi.auctionkoi.payload.request.WithdrawRequest;
import com.bidkoi.auctionkoi.pojo.Withdraw;

import java.util.List;

public interface IWithdrawService {
    WithdrawDTO createWithdraw(WithdrawRequest request, String accountId);

    WithdrawDTO approveWithdraw(Long withdrawId, Long staffId);

    WithdrawDTO rejectWithdraw(Long withdrawId, Long staffId, RejectWithdrawRequest request);

//    WithdrawDTO getWithdrawById(Long withdrawId);

    List<Withdraw> getAllWithdraw();

    WithdrawDTO getWithdrawByAccountId(String accountId);
}
