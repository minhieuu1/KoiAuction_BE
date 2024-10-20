package com.bidkoi.auctionkoi.service;


import com.bidkoi.auctionkoi.dto.AccountDTO;
import com.bidkoi.auctionkoi.dto.BidderDTO;
import com.bidkoi.auctionkoi.payload.request.AccountCreationRequest;
import com.bidkoi.auctionkoi.payload.request.LoginRequest;
import com.bidkoi.auctionkoi.payload.request.RegisterRequest;
import com.bidkoi.auctionkoi.payload.request.UpdatePasswordRequest;
import com.bidkoi.auctionkoi.payload.response.LoginResponse;
import com.bidkoi.auctionkoi.pojo.Account;
import com.bidkoi.auctionkoi.pojo.Bidder;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
   AccountDTO createAccount(AccountCreationRequest request);
   AccountDTO register(RegisterRequest request);
   List<Account> getAllAccounts();
   LoginResponse login(LoginRequest request);
   List<Bidder> getAll();
   Optional<Account> getAccountById(String id);
   Optional<Bidder> getBidderById(String accountId);

   public BidderDTO updateProfile(String accountID, BidderDTO bidderDTO);

   public void updatePassword(String accountId, UpdatePasswordRequest request);
}
