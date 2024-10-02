package com.bidkoi.controllers;

import com.bidkoi.dto.AccountDTO;
import com.bidkoi.dto.BidderDTO;
import com.bidkoi.exception.AppException;
import com.bidkoi.payload.request.AccountCreationRequest;
import com.bidkoi.payload.request.LoginRequest;
import com.bidkoi.payload.request.UpdatePasswordRequest;
import com.bidkoi.payload.response.ApiResponse;
import com.bidkoi.payload.response.LoginResponse;
import com.bidkoi.pojo.Account;
import com.bidkoi.pojo.Bidder;
import com.bidkoi.service.IAccountService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@CrossOrigin
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {
    IAccountService iAccountService;

    @PostMapping("/register")
    ApiResponse<AccountDTO> register(@RequestBody @Valid AccountCreationRequest request) {
        return ApiResponse.<AccountDTO>builder().data(iAccountService.createAccount(request)).build();
    }

    @PostMapping("/login")
    LoginResponse login(@RequestBody LoginRequest loginDTO) {
        return iAccountService.login(loginDTO);

    }

//    @GetMapping("/view-account/{id}")
//    public ResponseEntity<Optional<Account>> getAccountByID(@PathVariable String id){
//        Optional<Account> account = iAccountService.getAccountById(id);
//        return ResponseEntity.ok(account);
//    }

    @GetMapping("/view/{accountId}")
    public ResponseEntity<Optional<Bidder>> getBidderByID(@PathVariable String accountId){
        Optional<Bidder> account = iAccountService.getBidderById(accountId);
        return ResponseEntity.ok(account);
    }

    @PutMapping("/update-profile/{accountId}")
    public ResponseEntity<BidderDTO> updateProfile(@PathVariable String accountId, @RequestBody BidderDTO bidderDTO) {
        BidderDTO updatedProfile = iAccountService.updateProfile(accountId, bidderDTO);
        return ResponseEntity.ok(updatedProfile);
    }

}
