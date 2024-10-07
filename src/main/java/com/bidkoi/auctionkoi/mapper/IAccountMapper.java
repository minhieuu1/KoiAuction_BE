package com.bidkoi.auctionkoi.mapper;


import com.bidkoi.auctionkoi.dto.AccountDTO;
import com.bidkoi.auctionkoi.payload.request.AccountCreationRequest;
import com.bidkoi.auctionkoi.payload.request.RegisterRequest;
import com.bidkoi.auctionkoi.pojo.Account;
import org.mapstruct.Mapper;

@Mapper
public interface IAccountMapper {

    Account toAccount(RegisterRequest accountCreationRequest);
    Account toAccountCreation(AccountCreationRequest accountCreationRequest);
    AccountDTO toAccountDTO(Account account);



}
