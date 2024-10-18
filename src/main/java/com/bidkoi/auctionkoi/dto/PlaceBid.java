package com.bidkoi.auctionkoi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlaceBid {
    @JsonProperty("bidder")
    String userId;
    @JsonProperty("username")
    String username;
    @JsonProperty("price")
    String price;
    @JsonProperty("date")
    Date date;
    String status;
}
