package com.bidkoi.auctionkoi.service;


import com.bidkoi.auctionkoi.dto.KoiDTO;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.exception.ErrorCode;
import com.bidkoi.auctionkoi.mapper.IKoiMapper;
import com.bidkoi.auctionkoi.payload.request.KoiRequest;
import com.bidkoi.auctionkoi.payload.request.StatusRequest;
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
    public KoiDTO createKoi(KoiRequest request, Long breederId) {
        if(KoiRepo.existsByKoiId(request.getKoiId())) {
            throw new AppException(ErrorCode.KOI_ID_EXISTED);
        }
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
    public KoiDTO getKoiById(String koiId) {
        return mapper.toKoiDTO(KoiRepo.findById(koiId)
                .orElseThrow(()-> new AppException(ErrorCode.KOI_NOT_FOUND)));
    }


//    @Override
//    public KoiDTO getKoiById(Long koiId,Long breederId) {
//        Koi koi = KoiRepo.findById(koiId).
//                orElseThrow(()-> new AppException(ErrorCode.KOI_NOT_FOUND));
//        Breeder breeder = breederRepo.findById(breederId).
//                orElseThrow(()->new AppException(ErrorCode.BREEDER_NOT_FOUND));
//        koi.setBreeder(breeder);
//        return mapper.toKoiDTO(koi);
//
//    }

    @Override
    public String updateStatus(String koiId, int value) {
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

    @Override
    public KoiDTO updateKoi(String koiId, KoiRequest request) {
        Koi koi = KoiRepo.findById(koiId).
                orElseThrow(()->new AppException(ErrorCode.KOI_NOT_FOUND));
        mapper.updateKoi(koi, request);
        return mapper.toKoiDTO(KoiRepo.save(koi));
    }

    @Override
    public void deleteKoi(String koiId) {
        KoiRepo.deleteById(koiId);
    }
}
