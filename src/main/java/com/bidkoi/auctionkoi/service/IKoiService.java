package com.bidkoi.auctionkoi.service;



import com.bidkoi.auctionkoi.dto.KoiDTO;
import com.bidkoi.auctionkoi.payload.request.KoiRequest;
import com.bidkoi.auctionkoi.pojo.Koi;

import java.util.List;

public interface IKoiService {
    KoiDTO createKoi(KoiRequest request, Long breederId);
    List<Koi> getAllKois();
    KoiDTO getKoiById(Long koiId);
//    String updateStatus(String koiId, int status);
    List<Koi> getKoiByBreederId(Long breederId);
    void approveKoi(Long koiId);
    void rejectKoi(Long koiId);
    KoiDTO updateKoi(Long koiId, KoiRequest koiDTO);
    void deleteKoi(Long koiId);
}
