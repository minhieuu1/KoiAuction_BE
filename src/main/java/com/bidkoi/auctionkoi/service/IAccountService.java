package com.bidkoi.auctionkoi.service;


import com.bidkoi.auctionkoi.dto.AccountDTO;
import com.bidkoi.auctionkoi.payload.request.AccountCreationRequest;
import com.bidkoi.auctionkoi.payload.request.LoginRequest;
import com.bidkoi.auctionkoi.payload.response.LoginResponse;
import com.bidkoi.auctionkoi.pojo.Account;
import com.bidkoi.auctionkoi.pojo.Bidder;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
   AccountDTO createAccount(AccountCreationRequest request);
   LoginResponse login(LoginRequest request);
   List<Bidder> getAll();
   Optional<Account> getAccountById(String id);

   Optional<Bidder> getBidderById(String accountId);
}
