package com.bidkoi.auctionkoi.payload.request;

import lombok.Data;

@Data
public class NotificationFCM {
    String title;
    String message;
    String fcmToken;
}
