package com.bidkoi.auctionkoi.dto;

import com.bidkoi.auctionkoi.enums.AuctionStatus;
import com.bidkoi.auctionkoi.pojo.Room;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuctionDTO {

    Long auctionId;

    LocalDateTime startTime;
    LocalDateTime endTime;

    AuctionStatus status;
    List<Room> rooms;
}
