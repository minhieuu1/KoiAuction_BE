package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.BidderDTO;
import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.pojo.*;
import com.bidkoi.auctionkoi.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

//    private List<Room> getRoomsfromKoiList(List<Koi> koiList) {
//        List<Room> rooms = new ArrayList<>();
//        for (Koi koi : koiList) {
//            rooms = roomRepo.findByKoi(koiList);
//        }
//    }

    @Override
    public Bidder pickRandomBidder(Long roomID) {
        Room room = roomRepo.findById(roomID)
                .orElseThrow(()-> new AppException(ErrorCode.ROOM_NOT_FOUND));
        if(room.getWinner() != null) {
            throw new AppException(ErrorCode.WINNER_EXISTED);
        }
        List<Bid> bids = bidRepo.findByRoom(room);

        Random random = new Random();
        Bid winner = new Bid();

        if (bids.isEmpty()) {
            throw new IllegalArgumentException("No bidders found for the given Room ID.");
        } else if (bids.size() == 1) {
            winner = bids.get(0);
        } else {
            winner = bids.get(random.nextInt(bids.size()));
        }
        room.setWinner(winner.getBidder().getId());
        roomRepo.save(room);


        return winner.getBidder();
    }




}

