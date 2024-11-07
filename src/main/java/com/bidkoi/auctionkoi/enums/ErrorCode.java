package com.bidkoi.auctionkoi.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    USER_EXISTED(422,"Username already existed!"),
    USER_NOT_FOUND(404,"User not found"),
    EMAIL_EXISTED(422, "Email already existed!"),
    USERNAME_INVALID(400,"Username must be between 8 and 16 characters"),
    PHONE_INVALID(400,"Phone number must be 10 characters!!!"),
    UNAUTHENTICATED_USERNAME(400,"Invalid username!"),
    UNAUTHENTICATED_PASSWORD(401,"Invalid password!"),
    INVALID_EMAIL(401,"Invalid email address!"),
    KOI_NOT_FOUND(400,"Koi not found!"),
    KOI_EXISTED(400,"Koi already existed!"),
    BREEDER_NOT_FOUND(404,"Breeder not found!"),
    STAFF_NOT_FOUND(404,"Staff not found!"),
    STATUS_ERROR(401,"Koi status is not PENDING!"),
    ROLE_ERROR(401,"Role Error!"),
    INVALID_CURRENT_PASSWORD(401, "Invalid current password!!!"),  //Bad Request
    SENDMAIL_FAILED(405, "Can not send email!!!"),
    AUCTION_ID_EXISTED(422, "AuctionID already existed!!!"),
    AUCTION_ID_NOT_FOUND(404, "AuctionID not found!!! "),
    ROOM_NOT_FOUND(404, "Room ID not found!!!"),
    BID_ID_NOT_FOUND(404, "Bid ID not found!!!"),
    BIDDER_NOT_FOUND(404, "Bidder not found!!!"),
    INSUFFICIENT_BALANCE(402, "Insufficient  balance!!!"),
    INSUFFICIENT_INITIAL_PRICE(402, "The starting price must be greater than the original price!!!"),
    INVALID_AUCTION_DATE(401, "Start date cannot be in the past!!!"),
    INVALID_AUCTION_END_DATE(401, "Auction end time cannot be before start time!!!"),
    WALLET_NOT_FOUND(404, "Wallet not found!!!"),
    TRANSACTION_NOT_FOUND(404, "Transaction not found!!!"),
    WINNER_EXISTED(422, "Winner already existed!"),
    EMPTY_TOKEN(400, "Empty token!"),
    BALANCE_NOT_ENOUGH(400, "Balance not enough!"),
    BIDDER_EXISTED(422, "Bidder already existed!"),
    EXPIRE_TOKEN(401, "Token has expired"),
    ERROR_TOKEN(401,"Invalid Token"),
    SHIPPING_ID_NOT_FOUND(404,"SHIPPING ID not found!!!"),
    SHIPPING_EXISTED(422, "Shipping already existed!"),
    TOKEN_EXPIRED(401, "Token has expired"),
    TOKEN_ERROR(401, "Invalid token"),
    HAS_AUCTION_ACTIVE(400,"Has auction active already!"),
    AUCTION_SAME_TIME(400,"Auction same time already existed!"),
    TRANSACTION_COMPLETED(401, "Transaction has been completed!"),
    ROLLBACK_ERROR(401, "Rollback error!"),
    INVOICE_NOT_FOUND(404, "Invoice not found!!!"),
    AMOUNT_TOO_LOW(400, "Amount is lower than current bid!!!"),
    WINNER_NOT_EXIST(404, "Winner not existed!!!"),

    ;

    int code;
    String message;
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
