package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.BreederDTO;
import com.bidkoi.auctionkoi.pojo.Breeder;

import java.util.List;


public interface IBreederService {
//    BreederDTO createBreeder(Breeder breeder);

    List<Breeder> getAllBreeders();
}