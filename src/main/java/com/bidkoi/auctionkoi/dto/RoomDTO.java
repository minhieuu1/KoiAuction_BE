package com.bidkoi.auctionkoi.dto;


import com.bidkoi.auctionkoi.enums.AuctionStatus;
import com.bidkoi.auctionkoi.pojo.Koi;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomDTO {
    Long roomId;
    String type;

    Koi koi;
    Long auctionId;
    LocalDateTime startTime;
    LocalDateTime endTime;
    AuctionStatus status;
}
