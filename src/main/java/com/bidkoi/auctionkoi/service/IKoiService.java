package com.bidkoi.auctionkoi.service;



import com.bidkoi.auctionkoi.dto.KoiDTO;
import com.bidkoi.auctionkoi.payload.request.KoiCreationRequest;
import com.bidkoi.auctionkoi.payload.request.KoiRequest;
import com.bidkoi.auctionkoi.pojo.Breeder;
import com.bidkoi.auctionkoi.pojo.Koi;

import java.util.List;

public interface IKoiService {
    KoiDTO createKoi(KoiRequest request, Long breederId);
    List<Koi> getAllKois();
    KoiDTO getKoiById(String koiId);
    String updateStatus(String koiId, int status);
    KoiDTO updateKoi(String koiId, KoiRequest koiDTO);
    void deleteKoi(String koiId);
}
