package com.bidkoi.auctionkoi.controller;


import com.bidkoi.auctionkoi.dto.WalletDTO;
import com.bidkoi.auctionkoi.enums.ErrorCode;
import com.bidkoi.auctionkoi.exception.AppException;
import com.bidkoi.auctionkoi.payload.request.WalletRequest;
import com.bidkoi.auctionkoi.pojo.Transactions;
import com.bidkoi.auctionkoi.pojo.Wallet;

import com.bidkoi.auctionkoi.repository.ITransactionsRepository;
import com.bidkoi.auctionkoi.repository.IWalletRepository;
import com.bidkoi.auctionkoi.payload.response.ApiResponse;


import com.bidkoi.auctionkoi.service.IWalletService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
@CrossOrigin("*")

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletController {

    IWalletService walletService;



//    @PostMapping("/{accountId}")
//    ApiResponse<WalletDTO> createWallet(@RequestBody WalletRequest request, @PathVariable String accountId){
//        return ApiResponse.<WalletDTO>builder()
//                .data(walletService.createWallet(request, accountId))
//                .build();
//    }


    @PostMapping("/{accountId}")
    public ResponseEntity createWallet(@RequestBody WalletRequest request, @PathVariable String accountId) throws Exception {

        String vnPayURL = walletService.createUrl(request, accountId);
        return ResponseEntity.ok(vnPayURL);
    }

    @GetMapping("/vnpay-callback")
    public ResponseEntity<?> handleVnPayCallback(@RequestParam(name = "vnp_TxnRef") String vnpTxnRef,
                                                 @RequestParam(name = "vnp_ResponseCode") String responseCode,
                                                 @RequestParam(name = "vnp_Amount") String vnpAmount) {
        try {

            UUID transactionId = UUID.fromString(vnpTxnRef); // Chuyển mã giao dịch từ String sang UUID
            double amount = Double.parseDouble(vnpAmount);  // Chuyển số tiền từ chuỗi
            walletService.handleVnPayCallback(transactionId, responseCode, amount);

            return ResponseEntity.ok("Transaction processed successfully.");
//            // Kiểm tra nếu các tham số quan trọng tồn tại
//            if (vnpTxnRef == null || responseCode == null || vnpAmount == null) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing required parameters.");
//            }
//            else {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transaction failed.");
//            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/view/{accountId}")
    public ResponseEntity<WalletDTO> getWallet(@PathVariable String accountId) {
        WalletDTO wallet = walletService.getWallet(accountId);
        return ResponseEntity.ok(wallet);
    }

}
