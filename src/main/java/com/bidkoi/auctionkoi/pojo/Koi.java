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
public class Koi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KoiID")
    Long id;
    Double length;
    String varieties;
    String age;
    String sex;
    String image;
    String description;
    @Column(name = "Initial_price")
    Double initialPrice;
    @Column(name = "Final_price")
    Double finalPrice;
    @Builder.Default
    int status = 0;
    @ManyToOne
    @JoinColumn(name = "BreederID",referencedColumnName = "BreederID")
    Breeder breeder;
}
