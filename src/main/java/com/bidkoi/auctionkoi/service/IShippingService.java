package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.payload.request.InformationRequest;
import com.bidkoi.auctionkoi.pojo.Koi;
import com.bidkoi.auctionkoi.pojo.Shipping;

import java.util.List;

public interface IShippingService {
    boolean isExisted(Long koiId);
    Shipping createShipping(Long breederId,String bidderId,InformationRequest request);
    void confirmByBreeder(Long shippingId,String img);
    void confirmByBidder(Long shippingId,String img);
    void confirmInfo(InformationRequest request,Long koiId);
    List<Shipping> getByBreederId(Long breederId);
    List<Shipping> getByBidderId(String bidderId);
    List<Shipping> getByShippingId(Long shippingId);
}
