package com.bidkoi.auctionkoi.payload.request;



import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountCreationRequest {
    String username;
    String password;
    String email;
    String phone;
}
