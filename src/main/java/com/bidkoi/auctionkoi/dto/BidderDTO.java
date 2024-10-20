package com.bidkoi.auctionkoi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BidderDTO {
    String firstname;
    String lastname;
    String gender;

    @Pattern(regexp = "^0[0-9]{9}$", message = "PHONE_INVALID")
    String phone;
    @Email(message = "INVALID_EMAIL")
    String email;
    String address;

    Date birthday;
}
