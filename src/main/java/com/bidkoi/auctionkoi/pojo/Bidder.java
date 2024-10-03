package com.bidkoi.auctionkoi.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bidder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "BidderID")
    String id;
    @Column(name = "First_name")
    String firstname;
    @Column(name = "Last_name")
    String lastname;
    String gender;
    String address;
//    String phone;
//    String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date birthday;

    @OneToOne
    @JoinColumn(name = "AccountID", referencedColumnName = "AccountID")
    Account account;
}
