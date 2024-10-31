package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.BreederDTO;
import com.bidkoi.auctionkoi.payload.request.BreederRequest;
import com.bidkoi.auctionkoi.payload.request.FeeRequest;
import com.bidkoi.auctionkoi.pojo.Breeder;

import java.util.List;
import java.util.Optional;


public interface IBreederService {
//    BreederDTO createBreeder(BreederR breeder);

    List<Breeder> getAllBreeders();
    BreederDTO updateBreeder(String accountId,BreederRequest request);
    BreederDTO getBreeder(String accountId);

    BreederDTO requestKoi(Long breederID, FeeRequest feeRequest);
}
