package com.bidkoi.auctionkoi.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum StatusRequest {
    PENDING(0,"Pending"),
    ACCEPTED(1,"Accepted"),
    REJECTED(2,"Rejected"),;

    ;
    int value;
    String status;

    StatusRequest(int value,String status) {
        this.value = value;
        this.status = status;
    }
}
