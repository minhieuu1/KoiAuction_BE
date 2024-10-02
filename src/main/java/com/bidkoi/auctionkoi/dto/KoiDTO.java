package com.bidkoi.auctionkoi.dto;

import com.bidkoi.auctionkoi.pojo.Breeder;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KoiDTO {
    Long id;
    Double length;
    String varieties;
    String age;
    String sex;
    String image;
    String description;
    Double initialPrice;
    Double finalPrice;
    Breeder breeder;
}
