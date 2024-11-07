package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.dto.TransactionsDTO;
import com.bidkoi.auctionkoi.dto.WalletDTO;
import com.bidkoi.auctionkoi.pojo.Bid;
import com.bidkoi.auctionkoi.pojo.Transactions;
import com.bidkoi.auctionkoi.pojo.Wallet;
import com.bidkoi.auctionkoi.service.ITransactionService;
import com.bidkoi.auctionkoi.service.IWalletService;
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
    IWalletService walletService;
    ITransactionService service;

    @PutMapping("/rollback/{auctionId}")
    ResponseEntity<Void> refund(@PathVariable Long auctionId) {
        service.rollBack(auctionId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/rollback/{bidderId}/{koiId}")
    ResponseEntity<Void> refundToWinner(@PathVariable String bidderId,@PathVariable Long koiId) {
        service.rollbackToWinner(bidderId,koiId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/transferToBreeder/{koiId}")
    ResponseEntity<Void> refundToBreeder(@PathVariable Long koiId) {
        service.transferToBreeder(koiId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/rollback-to-breeder/{koiId}")
    ResponseEntity<String> rollbackToBreeder(@PathVariable Long koiId) {
        service.rollbackToBreeder(koiId);
        return ResponseEntity.ok("Rollback to breeder successful");
    }

    @GetMapping("/view/{accountId}")
    ResponseEntity<List<TransactionsDTO>> getTransaction(@PathVariable String accountId) {
        return ResponseEntity.ok(service.getTransactions(accountId));
    }

    @GetMapping
    ResponseEntity<List<TransactionsDTO>> getAllTransactions() {
        return ResponseEntity.ok(service.getAllTransactions());
    }
}
