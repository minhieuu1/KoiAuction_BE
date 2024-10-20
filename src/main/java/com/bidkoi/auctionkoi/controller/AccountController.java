package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.dto.AccountDTO;
import com.bidkoi.auctionkoi.dto.BidderDTO;
import com.bidkoi.auctionkoi.payload.request.AccountCreationRequest;
import com.bidkoi.auctionkoi.payload.request.LoginRequest;
import com.bidkoi.auctionkoi.payload.request.RegisterRequest;
import com.bidkoi.auctionkoi.payload.request.UpdatePasswordRequest;
import com.bidkoi.auctionkoi.payload.response.ApiResponse;
import com.bidkoi.auctionkoi.payload.response.LoginResponse;
import com.bidkoi.auctionkoi.pojo.Account;
import com.bidkoi.auctionkoi.pojo.Bidder;
import com.bidkoi.auctionkoi.service.IAccountService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@CrossOrigin("*")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {
    IAccountService iAccountService;

    @PostMapping("/register")
    ApiResponse<AccountDTO> register(@RequestBody @Valid RegisterRequest request) {
        return ApiResponse.<AccountDTO>builder().data(iAccountService.register(request)).build();
    }

    @PostMapping("/creation")
//    @PreAuthorize("hasAuthority('STAFF')")
    ApiResponse<AccountDTO> create(@RequestBody @Valid AccountCreationRequest request) {
        return ApiResponse.<AccountDTO>builder().data(iAccountService.createAccount(request)).build();
    }

    @GetMapping
    ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(iAccountService.getAllAccounts());
    }

    @PostMapping("/login")
    LoginResponse login(@RequestBody LoginRequest request) {
        return iAccountService.login(request);
    }

    @GetMapping("/profile/{accountId}")
    public ResponseEntity<Optional<Bidder>> getBidderByID(@PathVariable String accountId){
        Optional<Bidder> account = iAccountService.getBidderById(accountId);
        return ResponseEntity.ok(account);
    }

    @PutMapping("/update-profile/{accountId}")
    public ResponseEntity<BidderDTO> updateProfile(@PathVariable String accountId, @Valid @RequestBody BidderDTO bidderDTO) {
        BidderDTO updatedProfile = iAccountService.updateProfile(accountId, bidderDTO);
        return ResponseEntity.ok(updatedProfile);
    }

    @PostMapping("/update-password/{accountId}")
    public ResponseEntity<String> updatePassword(
            @PathVariable String accountId,
            @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        iAccountService.updatePassword(accountId, updatePasswordRequest);
        return ResponseEntity.ok("Password updated successfully.");
    }

}
