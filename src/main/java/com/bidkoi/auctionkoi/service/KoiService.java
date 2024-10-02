package com.bidkoi.auctionkoi.service;


import com.bidkoi.auctionkoi.dto.KoiDTO;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.exception.ErrorCode;
import com.bidkoi.auctionkoi.mapper.IKoiMapper;
import com.bidkoi.auctionkoi.payload.request.KoiCreationRequest;
import com.bidkoi.auctionkoi.payload.request.StatusRequest;
import com.bidkoi.auctionkoi.payload.response.KoiResponse;
import com.bidkoi.auctionkoi.pojo.Breeder;
import com.bidkoi.auctionkoi.pojo.Koi;
import com.bidkoi.auctionkoi.repository.IBreederRepository;
import com.bidkoi.auctionkoi.repository.IKoiRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KoiService implements IKoiService {

    IKoiRepository KoiRepo;
    IBreederRepository breederRepo;
    IKoiMapper mapper;

    @Override
    public KoiDTO createKoi(KoiCreationRequest request,Long breederId) {
        Koi koi = mapper.toKoi(request);
        Breeder breeder = breederRepo.findById(breederId).
                orElseThrow(()->new AppException(ErrorCode.BREEDER_NOT_FOUND));
        koi.setBreeder(breeder);
        return mapper.toKoiDTO(KoiRepo.save(koi));
    }

    @Override
    public List<Koi> getAllKois() {
        return KoiRepo.findAll();
    }

    @Override
    public KoiDTO getKoiById(Long koiId) {
        return mapper.toKoiDTO(KoiRepo.findById(koiId).
                orElseThrow(() -> new AppException(ErrorCode.KOI_NOT_FOUND)));

    }

    @Override
    public String updateStatus(Long koiId, int value) {
        String status="Pending";
        if(value != 0 && value != 1 && value != 2){
            throw new AppException(ErrorCode.STATUS_ERROR);
        }
        Koi koi = KoiRepo.findById(koiId).
                orElseThrow(()->new AppException(ErrorCode.KOI_NOT_FOUND));
        koi.setStatus(value);
        for(StatusRequest request:StatusRequest.values()){
            if(request.getValue() == value){
                status = request.getStatus();
            }
        }
        KoiRepo.save(koi);
        return status;
    }
}
