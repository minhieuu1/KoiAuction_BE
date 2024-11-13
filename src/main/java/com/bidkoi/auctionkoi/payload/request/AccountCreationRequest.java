package com.bidkoi.auctionkoi.payload.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountCreationRequest {
    @Size(min = 5, max = 16,message = "USERNAME_INVALID")
    String username;
    String password;
    String role;
}
