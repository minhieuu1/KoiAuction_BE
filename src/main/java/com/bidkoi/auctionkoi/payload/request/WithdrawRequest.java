package com.bidkoi.auctionkoi.payload.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WithdrawRequest {
    Double amount;
    String accountNumber;
    String description;
    LocalDateTime withdrawDate;
    String status;

}
