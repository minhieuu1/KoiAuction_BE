package com.bidkoi.auctionkoi.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum Role {
    BIDDER,
    BREEDER,
    STAFF,
    ;
}
