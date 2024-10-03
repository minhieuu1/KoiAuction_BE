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
public class Breeder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BreederID")
    Long breederID;
    @Column(name = "Breeder_name")
    String name;
    String address;
}
