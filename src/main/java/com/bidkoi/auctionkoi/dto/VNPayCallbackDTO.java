package com.bidkoi.auctionkoi.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VNPayCallbackDTO {
    private String vnp_TxnRef;
    private String vnp_ResponseCode;
    private String vnp_Amount;
}
