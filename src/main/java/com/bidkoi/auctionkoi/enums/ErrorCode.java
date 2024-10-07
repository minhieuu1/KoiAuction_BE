package com.bidkoi.auctionkoi.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    USER_EXISTED(422,"Username already existed!"),
    USER_NOT_FOUND(400,"User not found"),
    EMAIL_EXISTED(422, "Email already existed!"),
    USERNAME_INVALID(400,"Username must be between 8 and 16 characters"),
    PHONE_INVALID(400,"Phone number must be 10 characters"),
    UNAUTHENTICATED_USER(401,"Invalid password!"),
    UNAUTHENTICATED_PASSWORD(401,"Invalid password!"),
    INVALID_EMAIL(401,"Invalid email address!"),
    KOI_NOT_FOUND(400,"Koi not found!"),
    BREEDER_NOT_FOUND(400,"Breeder not found!"),
    STATUS_ERROR(401,"Koi status is not PENDING!"),
    KOI_ID_EXISTED(422,"Koi ID already existed!"),
    INVALID_CURRENT_PASSWORD(401, "Invalid current password!!!"),  //Bad Request
    SENDMAIL_FAILED(405, "Can not send email!!!"),
    ;
    int code;
    String message;
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
