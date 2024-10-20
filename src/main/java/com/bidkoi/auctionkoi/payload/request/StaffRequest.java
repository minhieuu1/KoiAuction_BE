package com.bidkoi.auctionkoi.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
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
    @Email(message = "INVALID_EMAIL")
    String email;
    @Size(min = 10,max = 10,message = "PHONE_INVALID")
    String phone;
}
