package com.bidkoi.auctionkoi.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDTO {
    String id;
    String username;
    String password;
    String email;
    String phone;
}
