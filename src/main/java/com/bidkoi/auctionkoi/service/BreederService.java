package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.BreederDTO;
import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.enums.TransactionsEnum;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.mapper.IBreederMapper;
import com.bidkoi.auctionkoi.payload.request.BreederRequest;
import com.bidkoi.auctionkoi.payload.request.FeeRequest;
import com.bidkoi.auctionkoi.pojo.*;
import com.bidkoi.auctionkoi.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BreederService implements IBreederService {
    IBreederRepository breederRepo;
    IAccountRepository accountRepo;
    IKoiRepository koiRepo;
    IWalletRepository walletRepo;
    ITransactionsRepository transactionRepo;

    IBreederMapper mapper;

    @Override
    public List<Breeder> getAllBreeders() {
        return breederRepo.findAll();
    }

    @Override
    public BreederDTO getBreeder(String accountId) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
        return mapper.toBreederDTO(breederRepo.findBreederByAccount(account));
    }

    @Override
    public BreederDTO updateBreeder(String accountId, BreederRequest request) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if(accountRepo.existsByEmailAndIdIsNot(request.getEmail(),accountId)) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }else if(accountRepo.existsByPhoneAndIdIsNot(request.getPhone(),accountId)){
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }


        account.setEmail(request.getEmail());
        account.setPhone(request.getPhone());
        accountRepo.save(account);

        Breeder breeder = breederRepo.findBreederByAccount(account);
        breeder.setAccount(account);
        mapper.updateBreeder(breeder, request);
        breederRepo.save(breeder);


        return mapper.toBreederDTO(breeder);
    }

    //Thanh toán phí khi đăng kí cá Koi
    @Override
    public BreederDTO requestKoi(Long breederID, FeeRequest feeRequest) {
        Breeder breeder = breederRepo.findById(breederID).
                orElseThrow(()->new AppException(ErrorCode.BREEDER_NOT_FOUND));

        Account account = breeder.getAccount();
        Wallet wallet = walletRepo.findWalletByAccount(account);
        if(wallet.getBalance() <  feeRequest.getFee()) {
            throw new AppException(ErrorCode.BALANCE_NOT_ENOUGH);
        }

        wallet.setBalance(wallet.getBalance()- feeRequest.getFee());
        walletRepo.save(wallet);

        Transactions transaction = Transactions.builder()
                .amount(-feeRequest.getFee())
                .date(LocalDateTime.now())
                .description("Request Fee")
                .type(TransactionsEnum.FEE)
                .status("COMPLETED")
                .wallet(wallet)
                .build();
        transactionRepo.save(transaction);

        return mapper.toBreederDTO(breeder);
    }


}
