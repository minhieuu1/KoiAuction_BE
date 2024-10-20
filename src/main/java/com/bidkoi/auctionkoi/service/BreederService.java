package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.BreederDTO;
import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.mapper.IBreederMapper;
import com.bidkoi.auctionkoi.payload.request.BreederRequest;
import com.bidkoi.auctionkoi.pojo.Account;
import com.bidkoi.auctionkoi.pojo.Breeder;
import com.bidkoi.auctionkoi.repository.IAccountRepository;
import com.bidkoi.auctionkoi.repository.IBreederRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BreederService implements IBreederService {
    IBreederRepository breederRepo;
    IAccountRepository accountRepo;
    IBreederMapper mapper;

    @Override
    public List<Breeder> getAllBreeders() {
        return breederRepo.findAll();
    }

    @Override
    public BreederDTO getBreeder(String accountId) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
        return mapper.toBreederDTO(breederRepo.findBreederByAccount(account));
    }

    @Override
    public BreederDTO updateBreeder(String accountId, BreederRequest request) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if(accountRepo.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }


        account.setEmail(request.getEmail());
        account.setPhone(request.getPhone());
        accountRepo.save(account);

        Breeder breeder = breederRepo.findBreederByAccount(account);
        breeder.setAccount(account);
        mapper.updateBreeder(breeder, request);
        breederRepo.save(breeder);


        return mapper.toBreederDTO(breeder);
    }


}
