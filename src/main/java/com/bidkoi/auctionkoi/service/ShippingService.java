package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.payload.request.ConfirmRequest;
import com.bidkoi.auctionkoi.payload.request.InformationRequest;
import com.bidkoi.auctionkoi.pojo.*;
import com.bidkoi.auctionkoi.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShippingService implements IShippingService {

    IShippingRepository shippingRepo;
    IKoiRepository koiRepo;
    IBidderRepository bidderRepo;
    IBreederRepository breederRepo;
    IStaffRepository staffRepo;


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
                .date(LocalDateTime.now())
                .build();
        return shippingRepo.save(shipping);
    }

    @Override
    public void confirmByBreeder(Long shippingId, ConfirmRequest request) {
        Shipping ship = shippingRepo.findById(shippingId)
                .orElseThrow(()-> new AppException(ErrorCode.SHIPPING_ID_NOT_FOUND));

        ship.setImgBreeder(request.getImg());
        ship.setBreederConfirm(request.getConfirm());
        shippingRepo.save(ship);
    }

    @Override
    public void confirmByBidder(Long shippingId, ConfirmRequest request) {
        Shipping ship = shippingRepo.findById(shippingId)
                .orElseThrow(()-> new AppException(ErrorCode.SHIPPING_ID_NOT_FOUND));

        ship.setImgBidder(request.getImg());
        ship.setBidderConfirm(request.getConfirm());
        shippingRepo.save(ship);
    }

    @Override
    public void confirmByStaff(Long staffId,Long shippingId, ConfirmRequest request) {
        Shipping ship = shippingRepo.findById(shippingId)
                .orElseThrow(()-> new AppException(ErrorCode.SHIPPING_ID_NOT_FOUND));
        Staff staff = staffRepo.findById(staffId)
                        .orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));

        ship.setStaffConfirm(request.getConfirm());
        ship.setDescription(request.getDes());
        ship.setStaff(staff);

        ship.setStatus("CONFIRMED");

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

    @Override
    public List<Shipping> getAllShipping() {
        return shippingRepo.findAll();
    }
}
