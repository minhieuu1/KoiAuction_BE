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
    @Column(name = "KoiID")
    String koiId;
    Double length;
    String varieties;
    String age;
    String sex;
    String image;
    String video;
    String description;
    int method;
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
