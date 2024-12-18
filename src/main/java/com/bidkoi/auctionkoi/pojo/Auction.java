package com.bidkoi.auctionkoi.pojo;

import com.bidkoi.auctionkoi.enums.AuctionStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AuctionID")
    Long auctionId;

    @Column(name = "StartTime")
    LocalDateTime startTime;
    @Column(name = "EndTime")
    LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    AuctionStatus status;


    @OneToMany(mappedBy = "auctionId")
    List<Room> rooms;
}
