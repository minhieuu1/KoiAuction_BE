package com.bidkoi.auctionkoi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message {
    @JsonProperty("senderName")
    String username;
    @JsonProperty("message")
    String message;
    String status;
}
