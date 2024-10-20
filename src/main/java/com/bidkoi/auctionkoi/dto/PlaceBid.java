package com.bidkoi.auctionkoi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaceBid {
    @JsonProperty("userId")
    String userId;
    @JsonProperty("username")
    String username;
    @JsonProperty("price")
    String price;
    @JsonProperty("date")
    Date date;
    String status;
}
