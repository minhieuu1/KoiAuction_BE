package com.bidkoi.auctionkoi.payload.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletRequest {

    @NotNull(message = "Balance cannot be null")
    @DecimalMin(value = "1.0", message = "Balance must be greater than 0")
    Double balance;
}
