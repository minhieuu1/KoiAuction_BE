package com.bidkoi.auctionkoi.service;


import com.bidkoi.auctionkoi.dto.BidDTO;
import com.bidkoi.auctionkoi.dto.PlaceBid;
import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.enums.TransactionsEnum;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.mapper.IBidMapper;
import com.bidkoi.auctionkoi.payload.response.Winner;
import com.bidkoi.auctionkoi.pojo.*;
import com.bidkoi.auctionkoi.repository.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BidService implements IBidService {
    IBidRepository bidRepo;
    IBidderRepository bidderRepo;
    IRoomRepository roomRepo;
    IWalletRepository walletRepo;
    IKoiRepository koiRepo;
    ITransactionsRepository transactionRepo;
    IBidMapper mapper;


    @Override
    public Bid registerBid(String bidderID, Long roomID) {
        Bidder bidder = bidderRepo.findById(bidderID).
                orElseThrow(()->new AppException(ErrorCode.BIDDER_NOT_FOUND));

        Room room = roomRepo.findById(roomID).
                orElseThrow(()-> new AppException(ErrorCode.ROOM_NOT_FOUND));

        Koi koi = room.getKoi();

        double deposit = koi.getInitialPrice()*0.2;
        log.info(String.valueOf(deposit));

        Account account = bidder.getAccount();
        Wallet wallet = walletRepo.findWalletByAccount(account);
        if(wallet.getBalance() <  deposit) {
            throw new AppException(ErrorCode.BALANCE_NOT_ENOUGH);
        }

        wallet.setBalance(wallet.getBalance()- deposit);
        walletRepo.save(wallet);

        Transactions transaction = Transactions.builder()
                .amount(deposit)
                .date(new Date(System.currentTimeMillis()))
                .description("")
                .type(TransactionsEnum.DEPOSIT)
                .wallet(wallet)
                .build();
        transactionRepo.save(transaction);

        Bid bid = bidRepo.findByBidderAndRoom(bidder, room);
        if(bid == null) {
            bid = Bid.builder()
                    .bidder(bidder)
                    .room(room)
                    .build();
        }else{
            throw new AppException(ErrorCode.BIDDER_EXISTED);
        }

        return bidRepo.save(bid);
    }

    @Override
    public boolean joinBids(String bidderID, Long roomID) {
        Bidder bidder = bidderRepo.findById(bidderID).
                orElseThrow(()->new AppException(ErrorCode.BIDDER_NOT_FOUND));

        Room room = roomRepo.findById(roomID).
                orElseThrow(()-> new AppException(ErrorCode.ROOM_NOT_FOUND));


        return bidRepo.existsByBidderAndRoom(bidder, room);

    }

    @Override
    public PlaceBid updateBid(Long roomID, PlaceBid placeBid) {
        Bidder bidder = bidderRepo.findById(placeBid.getUserId())
                .orElseThrow(()->new AppException(ErrorCode.BIDDER_NOT_FOUND));

        Room room = roomRepo.findById(roomID)
                .orElseThrow(()->new AppException(ErrorCode.ROOM_NOT_FOUND));

        Koi koi = room.getKoi();
        koi.setFinalPrice(Double.parseDouble(placeBid.getAmount()));
        koiRepo.save(koi);
        Bid bid = bidRepo.findByBidderAndRoom(bidder,room);

        room.setWinner(bidder.getAccount().getUsername());
        roomRepo.save(room);

        bid.setUsername(bidder.getAccount().getUsername());
        bid.setAmount(Double.parseDouble(placeBid.getAmount()));
        bid.setDate(new Date(System.currentTimeMillis()));
        bidRepo.save(bid);

        return PlaceBid.builder()
                .userId(bidder.getId())
                .username(bidder.getAccount().getUsername())
                .amount(placeBid.getAmount())
                .date(new Date(System.currentTimeMillis()))
                .status("MESSAGE")
                .build();
    }



    @Override
    public Winner getWinner(Long roomId){

        Room room = roomRepo.findById(roomId)
                .orElseThrow(()->new AppException(ErrorCode.ROOM_NOT_FOUND));
        return Winner.builder()
                .username(room.getWinner())
                .koi(room.getKoi())
                .build();
    }

    @Override
    public List<PlaceBid> getBids(Long roomID) {
        Room room = roomRepo.findById(roomID)
                .orElseThrow(()->new AppException(ErrorCode.ROOM_NOT_FOUND));

        List<Bid> bis = bidRepo.findByRoom(room) ;
        List<PlaceBid> listBids = mapper.toPlaceBids(bis);

        return listBids;
    }
}

