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

    @PutMapping("/rollback/{roomId}")
    ResponseEntity<Void> refund(@PathVariable Long roomId) {
        service.rollBack(roomId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/rollback/{bidderId}/{koiId}")
    ResponseEntity<Void> refundToWinner(@PathVariable String bidderId,@PathVariable Long koiId) {
        service.rollbackToWinner(bidderId,koiId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/rollbackToBreeder/{koiId}")
    ResponseEntity<Void> refundToBreeder(@PathVariable Long koiId) {
        service.rollbackToBreeder(koiId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/rollback-to-breeder/{koiId}")
    ResponseEntity<String> rollbackToBreeder(@PathVariable Long koiId) {
        service.rollbackToBreeder(koiId);
        return ResponseEntity.ok("Rollback to breeder successful");
    }
}
