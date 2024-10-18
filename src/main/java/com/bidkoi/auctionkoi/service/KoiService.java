package com.bidkoi.auctionkoi.service;


import com.bidkoi.auctionkoi.dto.KoiDTO;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.mapper.IKoiMapper;
import com.bidkoi.auctionkoi.payload.request.KoiRequest;
import com.bidkoi.auctionkoi.enums.KoiStatus;
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
        Koi koi = mapper.toKoi(request);
        koi.setStatus(KoiStatus.PENDING);
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
        return mapper.toKoiDTO(KoiRepo.findById(koiId)
                .orElseThrow(()-> new AppException(ErrorCode.KOI_NOT_FOUND)));
    }

    @Override
    public List<Koi> getKoiByBreederId(Long breederId) {
        Breeder breeder = breederRepo.findById(breederId).
                orElseThrow(()-> new AppException(ErrorCode.BREEDER_NOT_FOUND));
        return KoiRepo.findByBreeder(breeder);
    }

    @Override
    public void approveKoi(Long koiId) {
        Koi koi = KoiRepo.findById(koiId)
                .orElseThrow(()-> new AppException(ErrorCode.KOI_NOT_FOUND));
        if(!koi.getStatus().equals(KoiStatus.PENDING)){
            throw new AppException(ErrorCode.STATUS_ERROR);
        }
        koi.setStatus(KoiStatus.ACCEPTED);
        KoiRepo.save(koi);
    }

    @Override
    public void rejectKoi(Long koiId) {
        Koi koi = KoiRepo.findById(koiId)
                .orElseThrow(()-> new AppException(ErrorCode.KOI_NOT_FOUND));
        if(!koi.getStatus().equals(KoiStatus.PENDING)){
            throw new AppException(ErrorCode.STATUS_ERROR);
        }
        koi.setStatus(KoiStatus.REJECTED);
        KoiRepo.save(koi);
    }

    @Override
    public KoiDTO updateKoi(Long koiId, KoiRequest request) {
        Koi koi = KoiRepo.findById(koiId).
                orElseThrow(()->new AppException(ErrorCode.KOI_NOT_FOUND));
        mapper.updateKoi(koi, request);
        return mapper.toKoiDTO(KoiRepo.save(koi));
    }

    @Override
    public void deleteKoi(Long koiId) {
        KoiRepo.deleteById(koiId);
    }
}
