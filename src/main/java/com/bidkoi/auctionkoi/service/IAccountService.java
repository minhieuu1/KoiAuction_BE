package com.bidkoi.auctionkoi.service;


import com.bidkoi.auctionkoi.dto.AccountDTO;
import com.bidkoi.auctionkoi.payload.request.AccountCreationRequest;
import com.bidkoi.auctionkoi.payload.request.LoginRequest;
import com.bidkoi.auctionkoi.payload.response.LoginResponse;

public interface IAccountService {
   AccountDTO createAccount(AccountCreationRequest request);
   LoginResponse login(LoginRequest request);

}
