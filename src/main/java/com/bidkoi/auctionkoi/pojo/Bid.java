package com.bidkoi.auctionkoi.pojo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    Bidder bidderID;


    @ManyToOne
    @JoinColumn(name = "Room_No",referencedColumnName = "Room_No")
    Room roomNo;

}
