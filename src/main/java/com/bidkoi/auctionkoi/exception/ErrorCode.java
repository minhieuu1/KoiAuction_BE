package com.bidkoi.auctionkoi.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    USER_EXISTED(422,"User already existed!"),
    EMAIL_EXISTED(422, "Email already existed!"),
    USERNAME_INVALID(400,"Username must be between 8 and 16 characters"),
    PHONE_INVALID(400,"Phone number must be 10 characters"),
    UNAUTHENTICATED(401,"Invalid username or password!"),
    INVALID_EMAIL(400,"Invalid email address!"),
    ;
    int code;
    String message;
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
