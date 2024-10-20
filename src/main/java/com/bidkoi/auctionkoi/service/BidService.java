package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.BidDTO;
import com.bidkoi.auctionkoi.dto.PlaceBid;
import com.bidkoi.auctionkoi.enums.ErrorCode;
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
    ITransactionRepository transactionRepo;
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
                .type("Deposit")
                .wallet(wallet)
                .build();
        transactionRepo.save(transaction);

        Bid bid = Bid.builder()
                .bidder(bidder)
                .room(room)
                .build();
        return bidRepo.save(bid);
    }

    @Override
    public List<Bid> joinBids(String bidderID, Long roomID) {
        Bidder bidder = bidderRepo.findById(bidderID).
                orElseThrow(()->new AppException(ErrorCode.BIDDER_NOT_FOUND));

        Room room = roomRepo.findById(roomID).
                orElseThrow(()-> new AppException(ErrorCode.ROOM_NOT_FOUND));

        boolean exists = bidRepo.existsByBidderAndRoom(bidder, room);


        return bidRepo.findByRoom(room);

    }

    @Override
    public PlaceBid updateBid(Long roomID, PlaceBid placeBid) {
        Bidder bidder = bidderRepo.findById(placeBid.getUserId())
                .orElseThrow(()->new AppException(ErrorCode.BIDDER_NOT_FOUND));

        Room room = roomRepo.findById(roomID)
                .orElseThrow(()->new AppException(ErrorCode.ROOM_NOT_FOUND));

        Bid bid = bidRepo.findByBidderAndRoom(bidder,room);

        room.setWinner(bidder.getAccount().getUsername());
        roomRepo.save(room);

        bid.setUsername(bidder.getAccount().getUsername());
        bid.setAmount(Double.parseDouble(placeBid.getPrice()));
        bid.setDate(new Date(System.currentTimeMillis()));
        bidRepo.save(bid);

        return PlaceBid.builder()
                .userId(bidder.getId())
                .username(bidder.getAccount().getUsername())
                .price(placeBid.getPrice())
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

        List<Bid> bis = new ArrayList<>() ;
        List<PlaceBid> listBids = mapper.toPlaceBids(bis);

        return listBids;
    }
}

