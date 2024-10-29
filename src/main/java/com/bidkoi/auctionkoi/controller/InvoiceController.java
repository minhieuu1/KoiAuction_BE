package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.dto.InvoiceDTO;
import com.bidkoi.auctionkoi.service.IInvoiceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
@CrossOrigin("*")
@SecurityRequirement(name = "api")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceController {

    IInvoiceService invoiceService;

//    @PostMapping("/create")
//    public ResponseEntity createInvoice(@RequestBody InvoiceDTO invoiceDTO) {
//        InvoiceDTO invoice = invoiceService.createInvoice(invoiceDTO);
//        return ResponseEntity.ok(invoice);
//
//    }

    @PostMapping("/create{roomId}")
    public ResponseEntity createInvoice(@PathVariable Long roomId) {
        InvoiceDTO invoice = invoiceService.createInvoice(roomId);
        return ResponseEntity.ok(invoice);

    }

    @GetMapping("/get/{roomId}")
    public ResponseEntity getInvoice(@PathVariable Long roomId) {
        InvoiceDTO invoice = invoiceService.getInvoice(roomId);
        return ResponseEntity.ok(invoice);
    }
}
