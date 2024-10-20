package com.bidkoi.auctionkoi.enums;

import com.bidkoi.auctionkoi.exception.AppException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum KoiStatus {
    PENDING,
    ACCEPTED,
    REJECTED,
    AVAILABLE,
    SOLD
    ;
}
