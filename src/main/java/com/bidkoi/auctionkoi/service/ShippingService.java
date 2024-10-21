package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.pojo.Bidder;
import com.bidkoi.auctionkoi.pojo.Breeder;
import com.bidkoi.auctionkoi.pojo.Shipping;
import com.bidkoi.auctionkoi.repository.IBidderRepository;
import com.bidkoi.auctionkoi.repository.IBreederRepository;
import com.bidkoi.auctionkoi.repository.IShippingRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShippingService implements IShippingService {

    IShippingRepository shippingRepo;
    IBreederRepository breederRepo;
    IBidderRepository bidderRepo;

    @Override
    public Shipping createShipping(Long breederId, String bidderId) {
        Breeder breeder = breederRepo.findById(breederId)
                .orElseThrow(() -> new AppException(ErrorCode.BREEDER_NOT_FOUND));

        Bidder bidder = bidderRepo.findById(bidderId)
                .orElseThrow(() -> new AppException(ErrorCode.BIDDER_NOT_FOUND));

        boolean existed = shippingRepo.existsByBreederAndBidder(breeder, bidder);
        if (existed) {
            throw new AppException(ErrorCode.SHIPPING_EXISTED);
        }
        Shipping shipping = Shipping.builder()
                .breeder(breeder)
                .bidder(bidder)
                .date(LocalDateTime.now())
                .status("pending")
                .build();
        return shippingRepo.save(shipping);
    }

    @Override
    public void confirmByBreeder(Long shippingId,String img) {
        Shipping ship = shippingRepo.findById(shippingId)
                .orElseThrow(()-> new AppException(ErrorCode.SHIPPING_ID_NOT_FOUND));

        ship = Shipping.builder()
                .imgBreeder(img)
                .build();
    }

    @Override
    public void confirmByBidder(Long shippingId, String img) {
        Shipping ship = shippingRepo.findById(shippingId)
                .orElseThrow(()-> new AppException(ErrorCode.SHIPPING_ID_NOT_FOUND));

        ship = Shipping.builder()
                .imgBidder(img)
                .build();
    }
}
