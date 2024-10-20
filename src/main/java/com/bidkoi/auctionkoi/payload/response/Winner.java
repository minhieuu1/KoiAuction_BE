package com.bidkoi.auctionkoi.payload.response;

import com.bidkoi.auctionkoi.pojo.Bidder;
import com.bidkoi.auctionkoi.pojo.Koi;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Winner {
    String username;
    Bidder bidder;
    Koi koi;
}
