package com.bidkoi.auctionkoi.pojo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
    @JoinColumn(name = "KoiID", referencedColumnName = "KoiID")
    Koi koi;

    @ManyToOne
    @JoinColumn(name = "BidderID", referencedColumnName = "BidderID")
    Bidder bidder;

    @Column(name = "Breeder_Confirm_Img")
    String imgBreeder;

//    @Enumerated(EnumType.STRING)
    @Column(name = "Breeder_Confirm")
    String breederConfirm;

    @Column(name = "Bidder_Confirm_Img")
    String imgBidder;

    @Column(name = "Bidder_Confirm")
    String bidderConfirm;

    String name;
    String address;
    String phone;


    String status;
    @Column(name = "Staff_Confirm")
    String staffConfirm;
    String description;

    LocalDateTime date;

    @OneToOne
    @JoinColumn(name =  "StaffID", referencedColumnName = "StaffID")
    Staff staff;
}
