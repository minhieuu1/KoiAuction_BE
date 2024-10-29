package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.pojo.Bid;
import com.bidkoi.auctionkoi.service.ITransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
@SecurityRequirement(name = "api")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionController {

    ITransactionService service;

    @PostMapping("/rollback/{roomId}")
    ResponseEntity<List<Bid>> refund(@PathVariable Long roomId) {
        return ResponseEntity.ok(service.rollBack(roomId));
    }


    @PostMapping("/rollback-to-breeder/{koiId}")
    ResponseEntity<String> rollbackToBreeder(@PathVariable Long koiId) {
        service.rollbackToBreeder(koiId);
        return ResponseEntity.ok("Rollback to breeder successful");
    }
}
