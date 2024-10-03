package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.pojo.Breeder;
import com.bidkoi.auctionkoi.service.IBreederService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
