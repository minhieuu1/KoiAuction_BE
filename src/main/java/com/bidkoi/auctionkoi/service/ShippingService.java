package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.payload.request.InformationRequest;
import com.bidkoi.auctionkoi.pojo.Bidder;
import com.bidkoi.auctionkoi.pojo.Breeder;
import com.bidkoi.auctionkoi.pojo.Koi;
import com.bidkoi.auctionkoi.pojo.Shipping;
import com.bidkoi.auctionkoi.repository.IBidderRepository;
import com.bidkoi.auctionkoi.repository.IBreederRepository;
import com.bidkoi.auctionkoi.repository.IKoiRepository;
import com.bidkoi.auctionkoi.repository.IShippingRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShippingService implements IShippingService {

    IShippingRepository shippingRepo;
    IKoiRepository koiRepo;
    IBidderRepository bidderRepo;
    IBreederRepository breederRepo;


    @Override
    public boolean isExisted(Long koiId) {
        Koi koi = koiRepo.findById(koiId)
                .orElseThrow(() -> new AppException(ErrorCode.KOI_NOT_FOUND));

        return shippingRepo.existsByKoi(koi);
    }

    @Override
    public Shipping createShipping(Long koiId, String bidderId,InformationRequest request) {
        Koi koi = koiRepo.findById(koiId)
                .orElseThrow(() -> new AppException(ErrorCode.KOI_NOT_FOUND));

        Bidder bidder = bidderRepo.findById(bidderId)
                .orElseThrow(() -> new AppException(ErrorCode.BIDDER_NOT_FOUND));

        boolean existed = shippingRepo.existsByKoiAndBidder(koi, bidder);
        if (existed) {
            throw new AppException(ErrorCode.SHIPPING_EXISTED);
        }
        Shipping shipping = Shipping.builder()
                .koi(koi)
                .bidder(bidder)
                .name(request.getName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .build();
        return shippingRepo.save(shipping);
    }

    @Override
    public void confirmByBreeder(Long shippingId,String img) {
        Shipping ship = shippingRepo.findById(shippingId)
                .orElseThrow(()-> new AppException(ErrorCode.SHIPPING_ID_NOT_FOUND));

        ship.setImgBreeder(img);
        shippingRepo.save(ship);
    }

    @Override
    public void confirmByBidder(Long shippingId, String img) {
        Shipping ship = shippingRepo.findById(shippingId)
                .orElseThrow(()-> new AppException(ErrorCode.SHIPPING_ID_NOT_FOUND));

        ship.setImgBidder(img);
        shippingRepo.save(ship);
    }

    @Override
    public void confirmInfo(InformationRequest request, Long koiId) {
        Koi koi = koiRepo.findById(koiId)
                .orElseThrow(() -> new AppException(ErrorCode.KOI_NOT_FOUND));

        Shipping shipping = shippingRepo.findByKoi(koi);

        shipping.setName(request.getName());
        shipping.setAddress(request.getAddress());
        shipping.setPhone(request.getPhone());
        shippingRepo.save(shipping);
    }

    @Override
    public List<Shipping> getByBreederId(Long breederId) {
        Breeder breeder = breederRepo.findById(breederId)
                .orElseThrow(()-> new AppException(ErrorCode.BREEDER_NOT_FOUND));

        List<Koi> kois = koiRepo.findByBreeder(breeder);
        List<Shipping> shippings = shippingRepo.findByKoiIn(kois);
        return shippings;
    }

    @Override
    public List<Shipping> getByBidderId(String bidderId) {
        Bidder bidder = bidderRepo.findById(bidderId)
                .orElseThrow(() -> new AppException(ErrorCode.BIDDER_NOT_FOUND));

        List<Shipping> shippings = shippingRepo.findByBidder(bidder);
        return shippings;
    }

    @Override
    public List<Shipping> getByShippingId(Long shippingId) {
        return shippingRepo.findByShippingId(shippingId);
    }
}
