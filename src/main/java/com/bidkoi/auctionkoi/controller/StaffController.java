package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.service.IKoiService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
@CrossOrigin
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StaffController {
    IKoiService ikoiService;

    @PostMapping("/{koiId}/approve")
    public ResponseEntity<String> approve(@PathVariable String koiId) {
        ikoiService.approveKoi(koiId);
        return ResponseEntity.ok("Approved");
    }

    @PostMapping("/{koiId}/reject")
    public ResponseEntity<String> reject(@PathVariable String koiId) {
        ikoiService.rejectKoi(koiId);
        return ResponseEntity.ok("Rejected");
    }
}
