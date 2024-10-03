package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.pojo.Breeder;
import com.bidkoi.auctionkoi.repository.IBreederRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BreederService implements IBreederService {
    IBreederRepository repository;

    @Override
    public List<Breeder> getAllBreeders() {
        return repository.findAll();
    }


}
