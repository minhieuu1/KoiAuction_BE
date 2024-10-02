package com.bidkoi.service;

import com.bidkoi.dto.AccountDTO;
import com.bidkoi.dto.BidderDTO;
import com.bidkoi.payload.request.AccountCreationRequest;
import com.bidkoi.payload.request.LoginRequest;
import com.bidkoi.payload.request.UpdatePasswordRequest;
import com.bidkoi.payload.response.LoginResponse;
import com.bidkoi.pojo.Account;
import com.bidkoi.pojo.Bidder;

import java.util.Optional;

public interface IAccountService {
    AccountDTO createAccount(AccountCreationRequest request);
    LoginResponse login(LoginRequest request);
    Optional<Account> getAccountById(String id);

    Optional<Bidder> getBidderById(String accountId);





}
