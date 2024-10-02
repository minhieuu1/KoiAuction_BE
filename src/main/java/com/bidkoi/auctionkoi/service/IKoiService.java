package com.bidkoi.auctionkoi.service;



import com.bidkoi.auctionkoi.dto.KoiDTO;
import com.bidkoi.auctionkoi.payload.request.KoiCreationRequest;
import com.bidkoi.auctionkoi.pojo.Breeder;
import com.bidkoi.auctionkoi.pojo.Koi;

import java.util.List;

public interface IKoiService {
    KoiDTO createKoi(KoiCreationRequest request,Long breederId);
    List<Koi> getAllKois();
    KoiDTO getKoiById(Long koiId);
    String updateStatus(Long koiId, int status);
}
