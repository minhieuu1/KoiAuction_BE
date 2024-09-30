package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.dto.AccountDTO;
import com.bidkoi.auctionkoi.payload.request.AccountCreationRequest;
import com.bidkoi.auctionkoi.payload.request.LoginRequest;
import com.bidkoi.auctionkoi.payload.response.ApiResponse;
import com.bidkoi.auctionkoi.payload.response.LoginResponse;
import com.bidkoi.auctionkoi.service.IAccountService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@CrossOrigin()
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


}
