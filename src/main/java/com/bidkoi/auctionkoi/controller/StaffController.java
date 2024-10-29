package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.dto.BreederDTO;
import com.bidkoi.auctionkoi.dto.KoiDTO;
import com.bidkoi.auctionkoi.dto.StaffDTO;
import com.bidkoi.auctionkoi.payload.request.StaffRequest;
import com.bidkoi.auctionkoi.payload.response.ApiResponse;
import com.bidkoi.auctionkoi.pojo.Staff;
import com.bidkoi.auctionkoi.service.IKoiService;
import com.bidkoi.auctionkoi.service.IStaffService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "api")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StaffController {
    IKoiService ikoiService;
    IStaffService service;

    @PutMapping("/{staffId}/approve/{koiId}")
    public ResponseEntity<KoiDTO> approve(@PathVariable Long koiId, @PathVariable Long staffId) {
        KoiDTO response = ikoiService.approveKoi(koiId,staffId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{staffId}/reject/{koiId}")
    public ResponseEntity<KoiDTO> reject(@PathVariable Long koiId, @PathVariable Long staffId) {
        return ResponseEntity.ok(ikoiService.rejectKoi(koiId,staffId));
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
