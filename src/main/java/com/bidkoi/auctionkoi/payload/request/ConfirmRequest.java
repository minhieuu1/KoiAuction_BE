package com.bidkoi.auctionkoi.payload.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfirmRequest {
    String img;
    String confirm;
    String des;
}
