package com.bidkoi.auctionkoi.mapper;



import com.bidkoi.auctionkoi.dto.KoiDTO;
import com.bidkoi.auctionkoi.payload.request.KoiRequest;
import com.bidkoi.auctionkoi.pojo.Koi;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IKoiMapper {
    Koi toKoi(KoiRequest request);
    KoiDTO toKoiDTO(Koi koi);
    void updateKoi(@MappingTarget Koi koi, KoiRequest request);
}
