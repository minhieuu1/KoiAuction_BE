package com.bidkoi.auctionkoi.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BidDTO {

    String username;
    Double amount;
    Date date;

}
