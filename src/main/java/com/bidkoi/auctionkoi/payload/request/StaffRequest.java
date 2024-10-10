package com.bidkoi.auctionkoi.payload.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffRequest {

    String firstName;
    String lastName;
    String gender;
    String email;
    String phone;
}
