package com.bidkoi.auctionkoi.pojo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long no;

    @ManyToOne
    @JoinColumn(name = "BidderID", referencedColumnName = "BidderID")
    Bidder bidder;

    @ManyToOne
    @JoinColumn(name = "RoomID",referencedColumnName = "RoomID")
    Room room;

    Double amount;

//    @Column(name = "Bid_Time")
//    LocalDateTime bidTime;

}
