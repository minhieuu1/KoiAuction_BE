package com.bidkoi.auctionkoi.dto;

import com.bidkoi.auctionkoi.pojo.Account;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffDTO {

    Long staffId;
    String firstName;
    String lastName;
    String gender;
    Account account;
}

