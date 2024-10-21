package com.bidkoi.auctionkoi.pojo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ShippingID")
    Long shippingId;

    @ManyToOne
    @JoinColumn(name = "BreederID", referencedColumnName = "BreederID")
    Breeder breeder;

    @ManyToOne
    @JoinColumn(name = "BidderID", referencedColumnName = "BidderID")
    Bidder bidder;

    @Column(name = "Breeder_Confirm_Img")
    String imgBreeder;

    @Column(name = "Bidder_Confirm_Img")
    String imgBidder;

    LocalDateTime date;

    String status;

}
