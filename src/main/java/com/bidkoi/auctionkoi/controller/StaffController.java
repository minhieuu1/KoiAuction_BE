package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.dto.BreederDTO;
import com.bidkoi.auctionkoi.dto.StaffDTO;
import com.bidkoi.auctionkoi.payload.request.StaffRequest;
import com.bidkoi.auctionkoi.payload.response.ApiResponse;
import com.bidkoi.auctionkoi.pojo.Staff;
import com.bidkoi.auctionkoi.service.IKoiService;
import com.bidkoi.auctionkoi.service.IStaffService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
@CrossOrigin("*")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StaffController {
    IKoiService ikoiService;
    IStaffService service;

    @PostMapping("/{koiId}/approve")
    public ResponseEntity<String> approve(@PathVariable Long koiId) {
        ikoiService.approveKoi(koiId);
        return ResponseEntity.ok("Approved");
    }

    @PostMapping("/{koiId}/reject")
    public ResponseEntity<String> reject(@PathVariable Long koiId) {
        ikoiService.rejectKoi(koiId);
        return ResponseEntity.ok("Rejected");
    }

    @GetMapping("/profile/{accountId}")
    ResponseEntity<StaffDTO> getBreeder(@PathVariable("accountId") String accountId) {
        return ResponseEntity.ok(service.getStaff(accountId));
    }

    @PutMapping("/update-profile/{accountId}")
    ResponseEntity<Void> updateBreeder(@PathVariable String accountId, @RequestBody @Valid StaffRequest request) {
        service.updateStaff(accountId, request);
        return ResponseEntity.noContent().build();
    }
}
