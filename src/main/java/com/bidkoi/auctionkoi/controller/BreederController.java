package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.dto.BreederDTO;
import com.bidkoi.auctionkoi.payload.request.BreederRequest;
import com.bidkoi.auctionkoi.payload.response.ApiResponse;
import com.bidkoi.auctionkoi.pojo.Breeder;
import com.bidkoi.auctionkoi.service.IBreederService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/breeder")
@RequiredArgsConstructor
@CrossOrigin
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BreederController {
    IBreederService service;

    @GetMapping
    List<Breeder> getAllBreeders() {
        return service.getAllBreeders();
    }

    @PutMapping("/update/{accountId}")
    ApiResponse<BreederDTO> updateBreeder(@PathVariable String accountId,@RequestBody @Valid BreederRequest request) {
        return ApiResponse.<BreederDTO>builder()
                .data(service.updateBreeder(accountId,request))
                .build();
    }
}
