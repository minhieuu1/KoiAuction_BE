package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.payload.response.ApiResponse;
import com.bidkoi.auctionkoi.pojo.Shipping;
import com.bidkoi.auctionkoi.service.ShippingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipping")
@RequiredArgsConstructor
@CrossOrigin("*")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShippingController {
    ShippingService service;
    @PostMapping("/creation/{breederId}/{bidderId}")
    ResponseEntity<ApiResponse<Shipping>> create(@PathVariable Long breederId, @PathVariable String bidderId) {
        ApiResponse<Shipping> response = ApiResponse.<Shipping>builder().data(service.createShipping(breederId,bidderId)).build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
