package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.dto.BreederDTO;
import com.bidkoi.auctionkoi.payload.request.BreederRequest;
import com.bidkoi.auctionkoi.payload.response.ApiResponse;
import com.bidkoi.auctionkoi.pojo.Bidder;
import com.bidkoi.auctionkoi.pojo.Breeder;
import com.bidkoi.auctionkoi.service.IBreederService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/breeder")
@RequiredArgsConstructor
@CrossOrigin("*")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BreederController {
    IBreederService service;

    @GetMapping
    List<Breeder> getAllBreeders() {
        return service.getAllBreeders();
    }

    @GetMapping("/profile/{accountId}")
    public ResponseEntity<BreederDTO> getBreederByID(@PathVariable String accountId){
        return ResponseEntity.ok(service.getBreeder(accountId));
    }

    @PutMapping("/update-profile/{accountId}")
    ResponseEntity<Void> updateBreeder(@PathVariable String accountId,@RequestBody @Valid BreederRequest request) {
        service.updateBreeder(accountId,request);
        return ResponseEntity.noContent().build();
    }
}
