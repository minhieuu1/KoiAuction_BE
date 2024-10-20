package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.dto.WalletDTO;
import com.bidkoi.auctionkoi.payload.request.WalletRequest;
import com.bidkoi.auctionkoi.payload.response.ApiResponse;
import com.bidkoi.auctionkoi.pojo.Wallet;
import com.bidkoi.auctionkoi.service.IWalletService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
@CrossOrigin("*")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletController {

    IWalletService walletService;

    @PostMapping("/{accountId}")
    ApiResponse<WalletDTO> createWallet(@RequestBody WalletRequest request, @PathVariable String accountId) {
        return ApiResponse.<WalletDTO>builder()
                .data(walletService.createWallet(request,accountId))
                .build();

    }

}
