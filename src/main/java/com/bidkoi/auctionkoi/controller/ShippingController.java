package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.payload.request.ConfirmRequest;
import com.bidkoi.auctionkoi.payload.request.InformationRequest;
import com.bidkoi.auctionkoi.payload.response.ApiResponse;
import com.bidkoi.auctionkoi.pojo.Shipping;
import com.bidkoi.auctionkoi.service.IShippingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipping")
@RequiredArgsConstructor
@CrossOrigin("*")
@SecurityRequirement(name = "api")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShippingController {
    IShippingService service;

    @GetMapping("/koi/{koiId}")
    public ResponseEntity<Boolean> isExist(@PathVariable("koiId") Long koiId) {
        return ResponseEntity.ok(service.isExisted(koiId));
    }

    @PostMapping("/creation/{koiId}/{bidderId}")
    ResponseEntity<ApiResponse<Shipping>> create(@PathVariable Long koiId, @PathVariable String bidderId,@RequestBody InformationRequest request) {
        ApiResponse<Shipping> response = ApiResponse.<Shipping>builder().data(service.createShipping(koiId,bidderId,request)).build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/breeder/{shippingId}")
    ResponseEntity<Void> confirmByBreeder(@RequestBody ConfirmRequest request, @PathVariable Long shippingId) {
        service.confirmByBreeder(shippingId,request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/bidder/{shippingId}")
    ResponseEntity<Void> confirmByBidder(@RequestBody ConfirmRequest request, @PathVariable Long shippingId) {
        service.confirmByBidder(shippingId,request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/staff/{staffId}/{shippingId}")
    ResponseEntity<Void> confirmByStaff(@RequestBody ConfirmRequest request, @PathVariable Long shippingId,@PathVariable Long staffId){
        service.confirmByStaff(staffId,shippingId,request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/info/{koiId}")
    ResponseEntity<Void> confirmInfo(@RequestBody InformationRequest request, @PathVariable Long koiId) {
        service.confirmInfo(request,koiId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/bidder/{bidderId}")
    ResponseEntity<List<Shipping>> getByBidder(@PathVariable String bidderId) {
        return ResponseEntity.ok(service.getByBidderId(bidderId));
    }

    @GetMapping("/breeder/{breederId}")
    ResponseEntity<List<Shipping>> getByBreeder(@PathVariable Long breederId) {
        return ResponseEntity.ok(service.getByBreederId(breederId));
    }

    @GetMapping("/{shippingId}")
    ResponseEntity<List<Shipping>> getByShippingId(@PathVariable Long shippingId) {
        return ResponseEntity.ok(service.getByShippingId(shippingId));
    }

    @GetMapping()
    ResponseEntity<List<Shipping>> getAll() {
        return ResponseEntity.ok(service.getAllShipping());
    }



}
