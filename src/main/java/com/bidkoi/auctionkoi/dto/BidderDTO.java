package com.bidkoi.auctionkoi.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BidderDTO {
    String avatar;
    String firstname;
    String lastname;
    String gender;
    String phone;
    String email;
    String address;

    Date birthday;
}
