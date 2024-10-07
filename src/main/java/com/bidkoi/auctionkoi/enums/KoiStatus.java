package com.bidkoi.auctionkoi.enums;

import com.bidkoi.auctionkoi.exception.AppException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum KoiStatus {
    PENDING(0),
    ACCEPTED(1),
    REJECTED(2),
    AVAILABLE(3),
    SOLD(4)
    ;
    int value;

    KoiStatus(int value) {
        this.value = value;
    }

    public static KoiStatus fromValue(int value) {
        for (KoiStatus s : KoiStatus.values()) {
            if (s.value == value) {
                return s;
            }
        }
        throw new AppException(ErrorCode.STATUS_ERROR);
    }
}
