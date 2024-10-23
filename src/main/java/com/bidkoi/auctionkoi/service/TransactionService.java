package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.enums.ErrorCode;
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

//    @Override
//    public void rollBack(Long roomId) {
//        Room room = roomRepo.findById(roomId)
//                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));
//        Bid bid =
//    }
    @Override
    public List<Bid> rollBack(Long roomId) {


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
//        Transactions transaction = Transactions.builder()
//                .amount(deposit)
//                .date(new Date(System.currentTimeMillis()))
//                .description("")
//                .type(TransactionsEnum.REFUND)
//                .wallet(wallet)
//                .build();
//        transactionRepo.save(transaction);
        return bids;

    }

    private void refund(Bid bid,double deposit) {
        Bidder bidder = bid.getBidder();
        Account account = bidder.getAccount();
        Wallet wallet = walletRepo.findWalletByAccount(account);

        wallet.setBalance(wallet.getBalance()+deposit);
        walletRepo.save(wallet);
    }
}
