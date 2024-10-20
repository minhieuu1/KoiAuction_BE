package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.payload.request.PlaceBidRequest;
import com.bidkoi.auctionkoi.pojo.*;
import com.bidkoi.auctionkoi.repository.IBidRepository;
import com.bidkoi.auctionkoi.repository.IBidderRepository;
import com.bidkoi.auctionkoi.repository.IRoomRepository;
import com.bidkoi.auctionkoi.repository.IWalletRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BidService implements IBidService {
    IBidRepository bidRepo;
    IBidderRepository bidderRepo;
    IRoomRepository roomRepo;
    IWalletRepository walletRepo;

    @Override
    public Bid createBid(String bidderID, Long roomID) {
        Bidder bidder = bidderRepo.findById(bidderID).
                orElseThrow(()->new AppException(ErrorCode.BIDDER_NOT_FOUND));

        Room room = roomRepo.findById(roomID).
                orElseThrow(()-> new AppException(ErrorCode.ROOM_NOT_FOUND));

        Koi koi = room.getKoi();

        Account account = bidder.getAccount();
        Wallet wallet = walletRepo.findWalletByAccount(account);
        wallet.setBalance(wallet.getBalance() - koi.getInitialPrice());
        walletRepo.save(wallet);

        Bid bid = Bid.builder()
                .bidder(bidder)
                .room(room)
                .amount(koi.getInitialPrice())
                .build();
        return bidRepo.save(bid);
    }

    //Phương thức 2: One Bid Time
    @Override
    public Bid placeBid(String bidderID, Long roomID, PlaceBidRequest request) {
        Bidder bidder = bidderRepo.findById(bidderID).
                orElseThrow(()->new AppException(ErrorCode.BIDDER_NOT_FOUND));

        Room room = roomRepo.findById(roomID).
                orElseThrow(()-> new AppException(ErrorCode.ROOM_NOT_FOUND));

        Koi koi = room.getKoi();

        Account account = bidder.getAccount();
        Wallet wallet = walletRepo.findWalletByAccount(account);

        if (wallet.getBalance() < request.getAmount()){
            throw new AppException(ErrorCode.INSUFFICIENT_BALANCE);
        }
        else if (request.getAmount() < koi.getInitialPrice()){
            throw new AppException(ErrorCode.INSUFFICIENT_INITIAL_PRICE);
        }

        wallet.setBalance(wallet.getBalance() - request.getAmount());

        Bid bid = Bid.builder()
                .bidder(bidder)
                .room(room)
                .amount(request.getAmount())
                .build();
        return bidRepo.save(bid);
    }

    @Override
    public Bid getWinningBid(Long roomID){
        List<Bid> bids = bidRepo.findByRoom_RoomId(roomID);

//        return bids.stream()
//                .max(Comparator.comparingDouble(Bid::getAmount))
//                .orElseThrow(() -> new AppException(ErrorCode.BID_ID_NOT_FOUND));

         Bid winningBid = bids.stream()
                .max(Comparator.comparingDouble(Bid::getAmount))
                .orElseThrow(() -> new AppException(ErrorCode.BID_ID_NOT_FOUND));

         bids.forEach(bid -> {
             if(!bid.getNo().equals(winningBid.getNo())){
                 Wallet wallet = walletRepo.findWalletByAccount(bid.getBidder().getAccount());
                 wallet.setBalance(wallet.getBalance() + bid.getAmount());
                 walletRepo.save(wallet);
             }
         });

        return winningBid;
    }

    //Phương thức 3: Multiple Bid Time
    @Override
    public Bid placeMutipleBid(String bidderID, Long roomID, PlaceBidRequest request) {
        Bidder bidder = bidderRepo.findById(bidderID).
                orElseThrow(()->new AppException(ErrorCode.BIDDER_NOT_FOUND));

        Room room = roomRepo.findById(roomID).
                orElseThrow(()-> new AppException(ErrorCode.ROOM_NOT_FOUND));

        Koi koi = room.getKoi();

        Account account = bidder.getAccount();
        Wallet wallet = walletRepo.findWalletByAccount(account);

        if (wallet.getBalance() < request.getAmount()){
            throw new AppException(ErrorCode.INSUFFICIENT_BALANCE);
        }
        else if (request.getAmount() < koi.getInitialPrice()){
            throw new AppException(ErrorCode.INSUFFICIENT_INITIAL_PRICE);
        }
//        else if (request.getAmount() < ){
//            throw new AppException(ErrorCode.INSUFFICIENT_INITIAL_PRICE);
//        }

        wallet.setBalance(wallet.getBalance() - request.getAmount());
        walletRepo.save(wallet);

        Bid bid = Bid.builder()
                .bidder(bidder)
                .room(room)
                .amount(request.getAmount())
                .build();
        return bidRepo.save(bid);
    }

    public double getCurrentHighestBid(Long roomID){
        return bidRepo.findByRoom_RoomId(roomID).stream()
                .mapToDouble(Bid::getAmount)
                .max()
                .orElseThrow(() -> new AppException(ErrorCode.BID_ID_NOT_FOUND));
    }

    public List<Bid> getBidsInRoom(Long roomID){
        return bidRepo.findByRoom_RoomId(roomID);
    }

    public Bid getWinningMutipleBid(Long roomID){
        List<Bid> bids = bidRepo.findByRoom_RoomId(roomID);

        Bid winningBid = bids.stream()
                .max(Comparator.comparingDouble(Bid::getAmount))
                .orElseThrow(() -> new AppException(ErrorCode.BID_ID_NOT_FOUND));

        bids.forEach(bid -> {
            if(!bid.getNo().equals(winningBid.getNo())){
                Wallet wallet = walletRepo.findWalletByAccount(bid.getBidder().getAccount());
                wallet.setBalance(wallet.getBalance() + bid.getAmount());
                walletRepo.save(wallet);
            }
        });

        return winningBid;
    }
}

