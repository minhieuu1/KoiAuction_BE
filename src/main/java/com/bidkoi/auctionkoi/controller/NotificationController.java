package com.bidkoi.auctionkoi.controller;

import com.bidkoi.auctionkoi.payload.request.NotificationFCM;
import com.bidkoi.auctionkoi.service.NotificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/notification")
@RequiredArgsConstructor
@CrossOrigin("*")
@SecurityRequirement(name = "api")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class NotificationController {

    NotificationService notificationService;

    @PostMapping("/send-notification")
    public void sendNotification(@RequestBody NotificationFCM notificationFCM) {
        notificationService.sendNotification(notificationFCM);
    }



}
