package com.bidkoi.auctionkoi.controller;


import com.bidkoi.auctionkoi.dto.KoiDTO;
import com.bidkoi.auctionkoi.payload.request.KoiCreationRequest;
import com.bidkoi.auctionkoi.payload.request.KoiRequest;
import com.bidkoi.auctionkoi.payload.response.ApiResponse;
import com.bidkoi.auctionkoi.payload.response.KoiResponse;
import com.bidkoi.auctionkoi.pojo.Breeder;
import com.bidkoi.auctionkoi.pojo.Koi;
import com.bidkoi.auctionkoi.service.IKoiService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/koi")
@RequiredArgsConstructor
@CrossOrigin
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KoiController {
    IKoiService service;

    @PostMapping("/create/{breederId}")
    ApiResponse<KoiDTO> create(@RequestBody KoiRequest request, @PathVariable Long breederId) {
        return ApiResponse.<KoiDTO>builder()
                .data(service.createKoi(request,breederId))
                .build();
    }

    @GetMapping
    List<Koi> getAll() {
        return service.getAllKois();
    }

//    @GetMapping("/{koiId}/{breederId}")
//    ApiResponse<KoiDTO> get(@PathVariable Long koiId,@PathVariable Long breederId) {
//        return ApiResponse.<KoiDTO>builder()
//                data(service.getKoiById())
//                .build();
//    }

    @PutMapping("/{koiId}")
    KoiResponse<KoiDTO> updateStatus(@PathVariable String koiId , @RequestBody int status){
        return KoiResponse.<KoiDTO>builder()
                .koi(service.getKoiById(koiId))
                .status(service.updateStatus(koiId,status))
                .build();
    }

    @PutMapping("/update/{koiId}")
    ApiResponse<KoiDTO> update(@PathVariable String koiId , @RequestBody KoiRequest request){
        return ApiResponse.<KoiDTO>builder()
                .data(service.updateKoi(koiId,request))
                .build();
    }

    @DeleteMapping("/del/{koiId}")
    String delete(@PathVariable String koiId){
        service.deleteKoi(koiId);
        return "Deleted";
    }
}