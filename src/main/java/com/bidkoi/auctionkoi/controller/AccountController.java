package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.dto.AccountDTO;
import com.bidkoi.auctionkoi.dto.BidderDTO;
import com.bidkoi.auctionkoi.payload.request.*;
import com.bidkoi.auctionkoi.payload.response.ApiResponse;
import com.bidkoi.auctionkoi.payload.response.LoginResponse;
import com.bidkoi.auctionkoi.pojo.Account;
import com.bidkoi.auctionkoi.pojo.Bidder;
import com.bidkoi.auctionkoi.service.IAccountService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@CrossOrigin("*")
@SecurityRequirement(name = "api")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {
    IAccountService iAccountService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AccountDTO>> register(@RequestBody @Valid RegisterRequest request) {
        AccountDTO accountDTO = iAccountService.register(request);
        ApiResponse<AccountDTO> response = ApiResponse.<AccountDTO>builder().data(accountDTO).build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping("/creation")
//    @PreAuthorize("hasAuthority('STAFF')")
    public ResponseEntity<ApiResponse<AccountDTO>> create(@Valid @RequestBody AccountCreationRequest request) {
        ApiResponse<AccountDTO> response = ApiResponse.<AccountDTO>builder().data(iAccountService.createAccount(request)).build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(iAccountService.getAllAccounts());
    }

    @PostMapping("/login")
    LoginResponse login(@RequestBody LoginRequest request) {
        return iAccountService.login(request);
    }

//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
//        LoginResponse response = iAccountService.login(request);
//
//        if (!response.getToken().isEmpty()) {
//            // Đăng nhập thành công
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } else {
//
//            // Đăng nhập thất bại, có thể do thông tin không chính xác
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//    }

    @GetMapping("/profile/{accountId}")
    public ResponseEntity<Optional<Bidder>> getBidderByID(@PathVariable String accountId){
        Optional<Bidder> account = iAccountService.getBidderById(accountId);
        return ResponseEntity.ok(account);
    }

//    @PutMapping("/update-profile/{accountId}")
//    public ResponseEntity<BidderDTO> updateProfile(@PathVariable String accountId, @Valid @RequestBody BidderDTO bidderDTO) {
//        BidderDTO updatedProfile = iAccountService.updateProfile(accountId, bidderDTO);
//        return ResponseEntity.ok(updatedProfile);
//    }
    @PutMapping("/update-profile/{accountId}")
    public ResponseEntity<Void> updateProfile(@PathVariable String accountId, @Valid @RequestBody BidderDTO bidderDTO) {
        iAccountService.updateProfile(accountId, bidderDTO);
        return ResponseEntity.noContent().build();  // Trả về 204 No Content
    }
    @PostMapping("/update-password/{accountId}")
    public ResponseEntity<String> updatePassword(
            @PathVariable String accountId,
            @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        iAccountService.updatePassword(accountId, updatePasswordRequest);
        return ResponseEntity.ok("Password updated successfully.");
    }




    @PatchMapping("/fcm")
    public ResponseEntity updateFCM(@RequestBody UpdateFCMRequest updateFCMRequest){
        Account account = iAccountService.updateFCM(updateFCMRequest);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/number/bidder")
     public ResponseEntity<ApiResponse<Integer>> numberOfBidder() {
        ApiResponse<Integer> response = ApiResponse.<Integer>builder().data(iAccountService.numberOfBidder()).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/number/breeder")
    public ResponseEntity<ApiResponse<Integer>> numberOfBreeder() {
        ApiResponse<Integer> response = ApiResponse.<Integer>builder().data(iAccountService.numberOfBreeder()).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/number/staff")
    public ResponseEntity<ApiResponse<Integer>> numberOfStaff() {
        ApiResponse<Integer> response = ApiResponse.<Integer>builder().data(iAccountService.numberOfStaff()).build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/banned/{accountId}")
    public ResponseEntity<Reason> banned(@PathVariable String accountId,@RequestBody Reason reason) {
        iAccountService.bannedUser(accountId,reason);
        return ResponseEntity.ok(reason);

    }


}
