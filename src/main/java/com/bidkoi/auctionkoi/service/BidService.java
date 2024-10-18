package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.PlaceBid;
import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.payload.response.Winner;
import com.bidkoi.auctionkoi.pojo.*;
import com.bidkoi.auctionkoi.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BidService implements IBidService {
    IBidRepository bidRepo;
    IBidderRepository bidderRepo;
    IRoomRepository roomRepo;
    IWalletRepository walletRepo;
    IKoiRepository koiRepo;

    @Override
    public Bid createBid(String bidderID, Long roomID) {
        Bidder bidder = bidderRepo.findById(bidderID).
                orElseThrow(()->new AppException(ErrorCode.BIDDER_NOT_FOUND));

        Room room = roomRepo.findById(roomID).
                orElseThrow(()-> new AppException(ErrorCode.ROOM_NOT_FOUND));

        Koi koi = room.getKoi();

        Account account = bidder.getAccount();
        Wallet wallet = walletRepo.findWalletByAccount(account);
        wallet.setBalance(wallet.getBalance()- koi.getInitialPrice());
        walletRepo.save(wallet);


        Bid bid = Bid.builder()
                .bidder(bidder)
                .room(room)
                .amount(koi.getInitialPrice())
                .build();
        return bidRepo.save(bid);
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

        bid.setAmount(Double.parseDouble(placeBid.getPrice()));
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
                .build();
    }
}

