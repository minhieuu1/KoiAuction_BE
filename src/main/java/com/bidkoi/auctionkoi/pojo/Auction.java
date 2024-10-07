package com.bidkoi.auctionkoi.pojo;

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
    @Column(name = "Status")
    String status;

    @OneToMany(mappedBy = "auctionId",cascade = CascadeType.ALL, orphanRemoval = true)
    List<Room> rooms;
}
