package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.pojo.Shipping;

public interface IShippingService {
    Shipping createShipping(Long breederId,String bidderId);
    void confirmByBreeder(Long shippingId,String img);
    void confirmByBidder(Long shippingId,String img);
}
