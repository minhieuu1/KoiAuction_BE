package com.bidkoi.auctionkoi.controller;


import com.bidkoi.auctionkoi.dto.KoiDTO;
import com.bidkoi.auctionkoi.payload.request.KoiRequest;
import com.bidkoi.auctionkoi.payload.response.ApiResponse;
import com.bidkoi.auctionkoi.pojo.Koi;
import com.bidkoi.auctionkoi.service.IKoiService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/koi")
@RequiredArgsConstructor
@CrossOrigin("*")
@SecurityRequirement(name = "api")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KoiController {
    IKoiService service;

    @PostMapping("/creation/{breederId}")
    public ResponseEntity<ApiResponse<KoiDTO>> create(@RequestBody KoiRequest request, @PathVariable Long breederId) {
        KoiDTO createdKoi = service.createKoi(request, breederId);
        ApiResponse<KoiDTO> response = ApiResponse.<KoiDTO>builder()
                .data(createdKoi)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);  // Trả về 201 Created
    }

    @GetMapping
    List<Koi> getAll() {
        return service.getAllKois();
    }

    @GetMapping("/breeder/{breederId}")
    public List<Koi> getKoiByBreeder(@PathVariable Long breederId) {
        return service.getKoiByBreederId(breederId);
    }

    @PutMapping("/update/{koiId}")
    ResponseEntity<Void> update(@PathVariable Long koiId , @RequestBody KoiRequest request){
        service.updateKoi(koiId,request);
        return ResponseEntity.noContent().build();
    }


    //coi lai
    @DeleteMapping("/del/{koiId}")
    ResponseEntity<Void> delete(@PathVariable Long koiId){
        service.deleteKoi(koiId);
        return ResponseEntity.noContent().build();
    }
}
