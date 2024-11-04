package com.bidkoi.auctionkoi.dto;

import com.bidkoi.auctionkoi.pojo.Account;
import com.bidkoi.auctionkoi.pojo.Staff;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WithdrawDTO {
    Long withdrawId;
    Double amount;
    String accountNumber;
    String withdrawDate;
    String description;
    String status;

    Account account;
    Staff staff;
}
