package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.enums.KoiStatus;
import com.bidkoi.auctionkoi.enums.TransactionsEnum;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.pojo.*;
import com.bidkoi.auctionkoi.repository.*;
import jakarta.transaction.Transaction;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionService implements ITransactionService {

    IRoomRepository roomRepo;
    IWalletRepository walletRepo;
    IBidderRepository bidderRepo;
    ITransactionsRepository transactionRepo;
    IBidRepository bidRepo;
    IKoiRepository koiRepo;

    @Override
    public void rollBack(Long roomId) {


        Room room = roomRepo.findById(roomId).
                orElseThrow(()-> new AppException(ErrorCode.ROOM_NOT_FOUND));
        String winner = room.getWinner();
        log.info(winner);
        List<Bid> bids = bidRepo.findByUsernameIsNot(winner);

        Koi koi = room.getKoi();

        double deposit = koi.getInitialPrice()*0.2;

        for(Bid bid : bids) {
            refund(bid, deposit);
        }

//        Account account = bidder.getAccount();
//        Wallet wallet = walletRepo.findWalletByAccount(account);
//
//
//        wallet.setBalance(wallet.getBalance()+ deposit);
//        walletRepo.save(wallet);
//


    }

    private void refund(Bid bid,double deposit) {
        Bidder bidder = bid.getBidder();
        Account account = bidder.getAccount();
        Wallet wallet = walletRepo.findWalletByAccount(account);
        wallet.setBalance(wallet.getBalance()+deposit);
        walletRepo.save(wallet);
        Transactions transaction = Transactions.builder()
                .amount(deposit)
                .date(new Date(System.currentTimeMillis()))
                .description("")
                .type(TransactionsEnum.REFUND)
                .status("COMPLETED")
                .wallet(wallet)
                .build();
        transactionRepo.save(transaction);

    }

    @Override
    public void rollbackToWinner(String bidderId,Long koiId) {
        Koi koi = koiRepo.findById(koiId)
                .orElseThrow(()-> new AppException(ErrorCode.KOI_NOT_FOUND));

        double deposit = koi.getInitialPrice()*0.2;
        Bidder bidder = bidderRepo.findById(bidderId)
                .orElseThrow(()-> new AppException(ErrorCode.BIDDER_NOT_FOUND));
        Account account = bidder.getAccount();
        Wallet wallet = walletRepo.findWalletByAccount(account);
        wallet.setBalance(wallet.getBalance()+deposit);
        walletRepo.save(wallet);
        Transactions transaction = Transactions.builder()
                .amount(deposit)
                .date(new Date(System.currentTimeMillis()))
                .description("")
                .type(TransactionsEnum.REFUND)
                .status("COMPLETED")
                .wallet(wallet)
                .build();
        transactionRepo.save(transaction);
    }

    @Override
    public void transferToBreeder(Long koiId) {
        Koi koi = koiRepo.findById(koiId)
                .orElseThrow(()-> new AppException(ErrorCode.KOI_NOT_FOUND));
        double deposit = koi.getInitialPrice()*0.2;
        Breeder breeder = koi.getBreeder();
        Account account = breeder.getAccount();
        Wallet wallet = walletRepo.findWalletByAccount(account);
        wallet.setBalance(wallet.getBalance()+deposit);
        walletRepo.save(wallet);
        Transactions transaction = Transactions.builder()
                .amount(deposit)
                .date(new Date(System.currentTimeMillis()))
                .description("")
                .type(TransactionsEnum.REFUND)
                .status("COMPLETED")
                .wallet(wallet)
                .build();
        transactionRepo.save(transaction);
    }


    @Override
    public void rollbackToBreeder(Long koiId) {
        Koi koi = koiRepo.findById(koiId)
                .orElseThrow(()-> new AppException(ErrorCode.KOI_NOT_FOUND));


        if(koi.getStatus().equals(KoiStatus.REJECTED)){
            double deposit = koi.getInitialPrice() * 0.5;
            Breeder breeder = koi.getBreeder();
            Account account = breeder.getAccount();
            Wallet wallet = walletRepo.findWalletByAccount(account);
            wallet.setBalance(wallet.getBalance() + deposit);

            Transactions transaction = Transactions.builder()
                    .amount(deposit)
                    .date(new Date(System.currentTimeMillis()))
                    .description("Refund for breederId: " + breeder.getBreederID())
                    .type(TransactionsEnum.REFUND)
                    .status("COMPLETED")
                    .wallet(wallet)
                    .build();

            if(transaction.getStatus().equals("COMPLETED")) {
                throw new AppException(ErrorCode.TRANSACTION_COMPLETED);
            }
            walletRepo.save(wallet);
            transactionRepo.save(transaction);

        }
        else {
            throw new AppException(ErrorCode.ROLLBACK_ERROR);
        }
    }
}
